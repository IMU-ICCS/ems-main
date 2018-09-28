package eu.paasage.upperware.profiler.generator.service.camel.parser.impl;

import camel.core.CamelModel;
import camel.metric.CompositeMetric;
import camel.metric.RawMetric;
import camel.metric.impl.MetricVariableImpl;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.service.camel.*;
import eu.paasage.upperware.profiler.generator.service.camel.creator.VariableCreator;
import eu.paasage.upperware.profiler.generator.service.camel.parser.ExpressionService;
import eu.paasage.upperware.profiler.generator.service.camel.parser.Marker;
import eu.paasage.upperware.profiler.generator.service.camel.parser.elements.AuxExpressionElement;
import eu.paasage.upperware.profiler.generator.service.camel.parser.elements.ExpressionElement;
import eu.paasage.upperware.profiler.generator.service.camel.parser.elements.MetricElement;
import eu.paasage.upperware.profiler.generator.service.camel.parser.elements.VariableElement;
import eu.passage.upperware.commons.model.tools.CamelModelTool;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.parsertokens.Operator;
import org.mariuszgromada.math.mxparser.parsertokens.ParserSymbol;
import org.mariuszgromada.math.mxparser.parsertokens.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExpressionServiceImpl implements ExpressionService {


    private static final Predicate<Token> OPERATOR_PREDICATE = token -> token.tokenTypeId == Operator.TYPE_ID;

    private static final Predicate<Token> POWER_PREDICATE = OPERATOR_PREDICATE.and(token -> token.tokenId == Operator.POWER_ID);
    private static final Predicate<Token> DIVIDE_PREDICATE = OPERATOR_PREDICATE.and(token -> token.tokenId == Operator.DIVIDE_ID);
    private static final Predicate<Token> MULTIPLY_PREDICATE = OPERATOR_PREDICATE.and(token -> token.tokenId == Operator.MULTIPLY_ID);
    private static final Predicate<Token> MINUS_PREDICATE = OPERATOR_PREDICATE.and(token -> token.tokenId == Operator.MINUS_ID);
    private static final Predicate<Token> PLUS_PREDICATE = OPERATOR_PREDICATE.and(token -> token.tokenId == Operator.PLUS_ID);
    private static final Predicate<Token> SUPPORTED_OPERATOR_PREDICATE = POWER_PREDICATE.or(DIVIDE_PREDICATE)
            .or(MULTIPLY_PREDICATE).or(MINUS_PREDICATE).or(PLUS_PREDICATE);

    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;
    private MetricService metricService;
    private VariableCreatorFactory variableCreatorFactory;
    private CacheService<NodeCandidates> memcacheService;
    private NodeCandidatesService nodeCandidatesService;

    @Override
    public void parse(String finalExpressionName, String formula, ConstraintProblem cp, CamelModel camelModel, String cacheKey) {
        Expression expression = new Expression(formula);
        evaluateExpression(new StackCreator(expression.getCopyOfInitialTokens()), finalExpressionName, cp, camelModel, cacheKey);
    }

    private Token createAuxExpression(String name, int tokenLevel) {
        return createToken(name, tokenLevel, AuxExpressionElement.INSTANCE);
    }

    private Token createToken(String name, int tokenLevel, ExpressionElement expressionElement){
        Token token = new Token();
        token.tokenStr = name;
        token.tokenLevel = tokenLevel;
        token.looksLike = expressionElement.getLooksLike();
        token.tokenTypeId = expressionElement.getTypeId();
        return token;
    }

    private void removeBrackets(List<Token> tokens) {
        tokens.removeIf(token -> token.tokenStr.equals(ParserSymbol.LEFT_PARENTHESES_STR) || token.tokenStr.equals(ParserSymbol.RIGHT_PARENTHESES_STR));
    }

    private String getRandomString(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
        return generator.generate(5);
    }

    private void evaluateExpression(StackCreator stackCreator, String finalExpressionName, ConstraintProblem cp, CamelModel camelModel, String cacheKey) {
        stackCreator.print();

        int deepestLevel = stackCreator.getDeepestLevel();
        if (deepestLevel == StackCreator.DEEPEST_POSSIBLE_LEVEL) {
            log.info("Deepest level= {} finish evaluating", deepestLevel);
            return;
        }
        log.info("Deepest level= {} continue evaluating", deepestLevel);

        List<Marker> deepestGroups = stackCreator.getDeepestGroups(deepestLevel);
        if (CollectionUtils.isEmpty(deepestGroups)) {
            log.info("Looking for level: {}, found: 0. Finish calculation", deepestLevel);
            return;
        }
        log.info("Looking for level: {}, found: {}", deepestLevel, deepestGroups.size());

        int i = 1;
        for (Marker marker : deepestGroups) {
            log.debug("Group {}", i++);
            Token newToken = evaluateSubExpression(Marker.copyOf(marker), deepestLevel, finalExpressionName, cp, camelModel, cacheKey);
            stackCreator.repleaceGroup(marker.getTokens(), newToken);
        }
        evaluateExpression(stackCreator, finalExpressionName, cp, camelModel, cacheKey);
    }

    private Token evaluateSubExpression(Marker marker, int deepestLevel, String finalExpressionName, ConstraintProblem cp, CamelModel camelModel, String cacheKey) {
        int level = marker.getTokenLevel();
        removeBrackets(marker.getTokens());

        Token result = null;
        while (hasOperator(marker)) {
            Token operator = getPriorityOperator(marker);
            Token prevToken = getPreviousToken(marker, operator);
            Token nextToken = getNextToken(marker, operator);

            //TODO - how to pass name deeper
            String name = (isLastExpressionLevel(deepestLevel) && isLastOperation(marker)) ? finalExpressionName : "aux_" + getRandomString();

            Token newToken = createNewToken(prevToken, operator, nextToken, name, level -1, cp, camelModel, cacheKey);

            List<Token> tokensToAnalise = Stream.of(prevToken, operator, nextToken).collect(Collectors.toList());

            log.debug("The expression {} will be replaced by {} in expression {}",
                    getTokenToPrint(tokensToAnalise), getTokenToPrint(Collections.singletonList(newToken)),
                    getTokenToPrint(marker.getTokens()));

            repleaceGroupInMarker(marker.getTokens(), tokensToAnalise, newToken);
            result = newToken;
        }

        return result;
    }

    private boolean isLastExpressionLevel(int deepestLevel) {
        return deepestLevel == 0;
    }

    private boolean isLastOperation(Marker marker) {
        return getNumberOfOperators(marker) == 1;
    }

    private String getTokenToPrint(List<Token> tokens){
        return tokens.stream().map(token -> token.tokenStr).collect(Collectors.joining(",", "[", "]"));
    }

    private void repleaceGroupInMarker(List<Token> oryginalTokens, List<Token> tokensToRemove, Token newToken) {
        Token firstToken = tokensToRemove.get(0);
        int indexOfToken = oryginalTokens.indexOf(firstToken);

        oryginalTokens.removeAll(tokensToRemove);
        oryginalTokens.add(indexOfToken, newToken);
    }

    private Token createNewToken(Token prevToken, Token operator, Token nextToken, String finalName, int newLevel, ConstraintProblem cp, CamelModel camelModel, String cacheKey) {
        eu.paasage.upperware.metamodel.cp.NumericExpression prevExpression = getExpression(prevToken, cp, camelModel, cacheKey);
        eu.paasage.upperware.metamodel.cp.NumericExpression nextExpression = getExpression(nextToken, cp, camelModel, cacheKey);
        eu.paasage.upperware.metamodel.cp.OperatorEnum operatorEnum = getOperator(operator);

        ComposedExpression composedExpression = constraintService.createComposedExpression(operatorEnum, finalName, prevExpression, nextExpression);
        cp.getAuxExpressions().add(composedExpression);

        return createAuxExpression(composedExpression.getId(), newLevel);
    }

    private OperatorEnum getOperator(Token token) {
        if (token.tokenId == Operator.PLUS_ID) {
            return OperatorEnum.PLUS;
        } else if (token.tokenId == Operator.MINUS_ID) {
            return OperatorEnum.MINUS;
        } else if (token.tokenId == Operator.MULTIPLY_ID) {
            return OperatorEnum.TIMES;
        } else if (token.tokenId == Operator.DIVIDE_ID){
            return OperatorEnum.DIV;
        }
        throw new GeneratorException("Unsupported operation id: " + token.tokenId);
    }

    private eu.paasage.upperware.metamodel.cp.NumericExpression getExpression(Token token, ConstraintProblem cp, CamelModel camelModel, String cacheKey) {
        if (isNumericConstant(token)) {
            return constantService.searchOrCreateConstantByValue(cp.getConstants(), token.tokenValue);
        } else if (isAuxExpression(token)) {
            return getAuxExpressionsByName(token, cp).orElseThrow(() -> new GeneratorException("Little lime, there is no ComposedExpression with name " + token.tokenStr));
        } else if (isMetric(token)) {
            return getMetricByName(token, cp).orElseThrow(() -> new GeneratorException("Little lime, there is no CpMetric with name " + token.tokenStr));
        } else if (isVariable(token)) {
            return getVariableByName(token, cp).orElseThrow(() -> new GeneratorException("Little lime, there is no CpVariable with name " + token.tokenStr));
        } else if (isArgument(token)){

            //it might be anything, so we must check every collection
            Optional<CpMetric> cpMetric = getMetricByName(token, cp);
            if (cpMetric.isPresent()) {
                return cpMetric.get();
            }

            Optional<CpVariable> cpVariable = getVariableByName(token, cp);
            if (cpVariable.isPresent()) {
                return cpVariable.get();
            }

            Optional<ComposedExpression> cpAuxExpression = getAuxExpressionsByName(token, cp);
            if (cpAuxExpression.isPresent()) {
                return cpAuxExpression.get();
            }
            //it is also possible that we did not find anything so we must create expression by ourselves
            Optional<MetricVariableImpl> variableOpt = CamelModelTool.getVariable(camelModel, token.tokenStr);
            if (variableOpt.isPresent()) {
                MetricVariableImpl metricVariable = variableOpt.get();

                if (StringUtils.isBlank(metricVariable.getFormula())) {
                    //simple variable
                    if (CamelMetadataTool.isFromVariable(metricVariable)) {
                        CamelMetadata variableType = CamelMetadataTool.findVariableType(metricVariable);

                        //TODO getting cacheKey is ugly....
                        String componentName = metricVariable.getComponent().getName();
                        Map<Integer, List<NodeCandidate>> nc = memcacheService.load(cacheKey).get(componentName);

                        NumericDomain domain = createDomain(nc, variableType);

                        VariableCreator creator = variableCreatorFactory.getCreator(CamelModelTool.getType(metricVariable));
                        CpVariable newVariable = creator.createCpVariable(cp, variableType.variableType, componentName, domain, metricVariable.getName());
                        cp.getCpVariables().add(newVariable);
                        return newVariable;
                    } else {
                        throw new GeneratorException(format("Could not create variable for %s - missing or unsupported variable type", metricVariable.getName()));
                    }

                } else {
                    //TODO - Variables with formula
                    parse(metricVariable.getName(), metricVariable.getFormula(), cp, camelModel, cacheKey);
                }
            }

            Optional<RawMetric> rawMetricOptional = CamelModelTool.getRawMetric(camelModel, token.tokenStr);
            if (rawMetricOptional.isPresent()) {
                CpMetric metric = metricService.createCpMetric(rawMetricOptional.get());
                cp.getCpMetrics().add(metric);
                return metric;
            }

            Optional<CompositeMetric> compositeMetricOptional = CamelModelTool.getCompositeMetric(camelModel, token.tokenStr);
            if (compositeMetricOptional.isPresent()) {
                CpMetric metric = metricService.createCpMetric(compositeMetricOptional.get());
                cp.getCpMetrics().add(metric);
                return metric;
            }
        }
        throw new GeneratorException("Unsupported type: " + token.tokenTypeId + " with name: " + token.tokenStr);
    }

    private NumericDomain createDomain(Map<Integer, List<NodeCandidate>> nodeCandidates, CamelMetadata variableType) {
        switch (variableType) {
            case CORES:
                return variableService.createIntegerListDomain(nodeCandidatesService.getValuesForCores(nodeCandidates));
            case RAM:
                return variableService.createLongListDomain(nodeCandidatesService.getValuesForRam(nodeCandidates));
            case STORAGE:
                return variableService.createIntegerListDomain(nodeCandidatesService.getValuesForStorage(nodeCandidates));
            default:
                throw new GeneratorException("Unsupported type " + variableType.name());
        }
    }

    private Optional<ComposedExpression> getAuxExpressionsByName(Token token, ConstraintProblem cp) {
        return constraintService.getByName(cp.getAuxExpressions(), token.tokenStr);
    }

    private Optional<CpMetric> getMetricByName(Token token, ConstraintProblem cp) {
        return metricService.getByName(cp.getCpMetrics(), token.tokenStr);
    }

    private Optional<CpVariable> getVariableByName(Token token, ConstraintProblem cp) {
        return variableService.getByName(cp.getCpVariables(), token.tokenStr);
    }

    private Token getPreviousToken(Marker marker, Token operator) {
        return getToken(marker, marker.getTokens().indexOf(operator)-1);
    }

    private Token getNextToken(Marker marker, Token operator) {
        return getToken(marker, marker.getTokens().indexOf(operator)+1);
    }

    private Token getToken(Marker marker, int index){
        return marker.getTokens().get(index);
    }

    private Token getPriorityOperator(Marker marker) {
        Optional<Token> powerToken = lookForPowerOperator(marker);
        if (powerToken.isPresent()) {
            return powerToken.get();
        }

        Optional<Token> divideOperator = lookForDivideOperator(marker);
        if (divideOperator.isPresent()) {
            return divideOperator.get();
        }

        Optional<Token> multiplyOperator = lookForMultiplyOperator(marker);
        if (multiplyOperator.isPresent()) {
            return multiplyOperator.get();
        }

        Optional<Token> minusOperator = lookForMinusOperator(marker);
        if (minusOperator.isPresent()) {
            return minusOperator.get();
        }

        Optional<Token> plusOperator = lookForPlusOperator(marker);
        if (plusOperator.isPresent()) {
            return plusOperator.get();
        }

        Token token = lookForOperator(marker, OPERATOR_PREDICATE).orElseThrow(() -> new GeneratorException("Could not find operator!!"));
        throw new GeneratorException("Unknown operator " + token.toString());
    }

    private boolean hasOperator(Marker marker) {
        return lookForOperator(marker, OPERATOR_PREDICATE).isPresent();
    }

    private long getNumberOfOperators(Marker marker) {
        return marker.getTokens().stream().filter(SUPPORTED_OPERATOR_PREDICATE).count();
    }

    private Optional<Token> lookForPowerOperator(Marker marker){
        return lookForOperator(marker, POWER_PREDICATE);
    }

    private Optional<Token> lookForDivideOperator(Marker marker){
        return lookForOperator(marker, DIVIDE_PREDICATE);
    }

    private Optional<Token> lookForMultiplyOperator(Marker marker){
        return lookForOperator(marker, MULTIPLY_PREDICATE);
    }

    private Optional<Token> lookForMinusOperator(Marker marker){
        return lookForOperator(marker, MINUS_PREDICATE);
    }

    private Optional<Token> lookForPlusOperator(Marker marker){
        return lookForOperator(marker, PLUS_PREDICATE);
    }

    private Optional<Token> lookForOperator(Marker marker, Predicate<Token> predicate) {
        return marker.getTokens().stream().filter(predicate).findFirst();
    }

    private boolean isNumericConstant(Token token){
        return token.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID && token.tokenId == ParserSymbol.NUMBER_ID;
    }

    private boolean isVariable(Token token){
        return token.tokenTypeId == VariableElement.INSTANCE.getTypeId();
    }

    private boolean isMetric(Token token){
        return token.tokenTypeId == MetricElement.INSTANCE.getTypeId();
    }

    private boolean isAuxExpression(Token token){
        return token.tokenTypeId == AuxExpressionElement.INSTANCE.getTypeId();
    }

    private boolean isArgument(Token token) {
         return token.tokenTypeId == -1;
    }

}
