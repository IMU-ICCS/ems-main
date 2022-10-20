package eu.morphemic.facade.proto;

import com.google.protobuf.GeneratedMessageV3;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class ReflectionHelper<M extends GeneratedMessageV3, B extends GeneratedMessageV3.Builder> {

	private final Class<M> messageClass;

	public ReflectionHelper(Class<M> messageClass) {
		this.messageClass = messageClass;
	}

	public B createBuilderInstance() {
		try {
			Method method = messageClass.getMethod("newBuilder", new Class[0]);
			return (B) method.invoke(messageClass, new Object[0]);
		}
		catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			// TODO error handling
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public void prepareBuilder(B builder, Map<String, Object> data) {
		Class<B> builderClass = (Class<B>) builder.getClass();
		for(Map.Entry<String, Object> entry : data.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			try {
				String setterName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
				for(Method m : builderClass.getMethods()) {
					if(m.getName().equalsIgnoreCase(setterName) && m.getParameterTypes().length == 1) {
						Class<?> para = m.getParameterTypes()[0];
						Method method = builderClass.getDeclaredMethod(setterName, para);
						log.info("!!!!!!!!  Invoking " + builderClass.getSimpleName() + "." + method.getName() + "("+ value +")");
						method.invoke(builder, value);
						break;
					}
				}
			}
			catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				// TODO error handling
				log.error(e.getMessage(), e);
			}
		}
	}

	public M parseFromBytes(byte[] bytes) {
		try {
			Method method = messageClass.getMethod("parseFrom", new Class[] {byte[].class});
			return (M) method.invoke(messageClass, bytes);
		}
		catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			// TODO error handling
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
