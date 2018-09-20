package eu.paasage.upperware.profiler.generator.service.camel.parser.impl;

import eu.paasage.upperware.profiler.generator.service.camel.parser.Marker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.mariuszgromada.math.mxparser.mXparser;
import org.mariuszgromada.math.mxparser.parsertokens.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class StackCreator {

    public static final int DEEPEST_POSSIBLE_LEVEL = -1;

    private LinkedList<Token> stack;

    StackCreator(List<Token> tokens) {
        this.stack = new LinkedList<>(tokens);
    }

    List<Marker> getDeepestGroups(int level) {

        List<Marker> result = new ArrayList<>();
        List<Token> sublist = new ArrayList<>();

        for (Token token : stack) {

            if (token.tokenLevel < level && CollectionUtils.isNotEmpty(sublist)){
                //save
                result.add(new Marker(sublist));
                sublist = new ArrayList<>();
            }

            if (token.tokenLevel == level){
                sublist.add(token);
            }
        }

        if (CollectionUtils.isNotEmpty(sublist)){
            //save
            result.add(new Marker(sublist));
        }

        return result;
    }

    int getDeepestLevel() {
        return stack.stream().mapToInt(token -> token.tokenLevel).boxed().max(Integer::compareTo).orElse(-1);
    }

    void repleaceGroup(List<Token> tokensToRemove, Token token) {
        Token firstToken = tokensToRemove.get(0);
        int indexOfToken = stack.indexOf(firstToken);

        stack.removeAll(tokensToRemove);
        stack.add(indexOfToken, token);
    }

    void print(){
        if (log.isDebugEnabled()){
            log.debug("Full expression:");
            mXparser.consolePrintTokens(stack);
        }
    }
}
