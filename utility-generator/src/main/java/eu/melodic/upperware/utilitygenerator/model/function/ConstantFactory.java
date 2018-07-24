package eu.melodic.upperware.utilitygenerator.model.function;

import org.mariuszgromada.math.mxparser.Constant;

public class ConstantFactory {


    public static Constant createConstant(Element element){
        if (element instanceof RealElement){
            return new Constant(element.getName(), ((RealElement) element).getValue());
        }
        else { //Integer
            return new Constant(element.getName(), ((IntElement) element).getValue());
        }
    }
}
