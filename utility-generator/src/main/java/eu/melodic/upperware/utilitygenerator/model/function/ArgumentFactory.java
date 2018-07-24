package eu.melodic.upperware.utilitygenerator.model.function;

import org.mariuszgromada.math.mxparser.Argument;

public class ArgumentFactory {


    public static Argument createArgument(Element element){

        if (element instanceof IntElement){
            return new Argument(element.getName(), ((IntElement) element).getValue());
        }
        else { //RealElement
            return new Argument(element.getName(), ((RealElement) element).getValue());
        }
    }
}
