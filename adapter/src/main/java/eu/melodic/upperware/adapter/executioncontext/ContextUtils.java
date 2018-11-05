package eu.melodic.upperware.adapter.executioncontext;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class ContextUtils {

    protected  <E> List<E> synchronizedList() {
        return Collections.synchronizedList(Lists.newLinkedList());
    }

}
