package org.burningwave.core;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.MethodCriteria;
import org.burningwave.core.function.MultiParamsConsumer;
import org.burningwave.core.function.MultiParamsFunction;
import org.burningwave.core.function.MultiParamsPredicate;
import org.burningwave.core.service.Service;
import org.junit.jupiter.api.Test;

public class FunctionalInterfaceFactoryTest extends BaseTest {
	
	@Test
	public void getOrBuildFunctionClassTestOne() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("apply"))
				.and().parameterType((params, idx) -> idx == 0 && params[idx].equals(Object.class))
				.and().parameterType((params, idx) -> idx == 1 && params[idx].equals(String.class))
				.and().parameterType((params, idx) -> idx == 2 && params[idx].equals(String.class)),
				Service.class				
			);
			MultiParamsFunction<String> virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.apply(new Service(), "Hello", "World!", "How are you?");
		});
	}
	
	@Test
	public void getOrBuildPredicateClassTestOne() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("test"))
				.and().parameterType((params, idx) -> idx == 0 && params[idx].equals(Object.class))
				.and().parameterType((params, idx) -> idx == 1 && params[idx].equals(String.class))
				.and().parameterType((params, idx) -> idx == 2 && params[idx].equals(String.class)),
				Service.class				
			);
			MultiParamsPredicate virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.test(new Service(), new Object(), "World!", "How are you?");
		});
	}
	
	@Test
	public void getOrBuildFunctionClassTestTwo() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("apply"))
				.and().parameterType((params, idx) -> idx == 0 && params[idx].equals(String.class))
				.and().parameterTypes((params) -> params.length == 1),
				Service.class
			);
			BiFunction<Service, String, String> bindedFunction = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			bindedFunction.apply(
				new Service(), "Hello World!"
			);
		});
	}
	
	@Test
	public void getOrBuildFunctionClassTestThree() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("staticApply"))
				.and().parameterType((params, idx) -> idx == 0 && params[idx].equals(Object.class))
				.and().parameterType((params, idx) -> idx == 1 && params[idx].equals(String.class))
				.and().parameterType((params, idx) -> idx == 2 && params[idx].equals(String.class)),
				Service.class				
			);
			MultiParamsFunction<String> virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.apply("Hello", "World!", "How are you?");
		});
	}
	
	@Test
	public void getOrBuildSupplierClassTestOne() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("retrieve")).and().parameterTypes(params -> params.length == 0),
				Service.class				
			);
			Supplier<Object> virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.get();
		});
	}
	
	@Test
	public void getOrBuildSupplierClassTestTwo() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("supply")).and().parameterTypes(params -> params.length == 0),
				Service.class				
			);
			Function<Service, Service> virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.apply(new Service());
		});
	}
	
	@Test
	//Not works
	public void getOrBuildConsumerClassTestOne() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("accept"))
				.and().parameterType((params, idx) -> idx == 0 && params[idx].equals(String.class))
				.and().parameterType((params, idx) -> idx == 1 && params[idx].equals(String.class))
				.and().parameterType((params, idx) -> idx == 2 && params[idx].equals(String.class)),
				Service.class				
			);
			MultiParamsConsumer virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.accept(new Service(), "Hello", "World!", "How are you?");
		});
	}
	
	@Test
	public void getOrBuildConsumerClassTestTwo() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("accept")).and().parameterTypes(params -> params.length == 1),
				Service.class				
			);
			BiConsumer<Service, String> virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.accept(new Service(), "hello!");
		});
	}
	
	@Test
	public void getOrBuildConsumerClassTestThree() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("staticAccept")).and().parameterTypes(params -> params.length == 4),
				Service.class				
			);
			MultiParamsConsumer virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.accept(new Service(), "Hello ", "world!", "How are you?");
		});
	}
	
	@Test
	public void getOrBuildRunnableClassTestOne() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("run")).and().parameterTypes(params -> params.length == 0),
				Service.class				
			);
			Consumer<Service> virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.accept(new Service());
		});
	}
	
	@Test
	public void getOrBuildRunnableClassTestTwo() throws Throwable {
		ComponentSupplier componentSupplier = getComponentSupplier();
		testDoesNotThrow(() -> {
			Method mth = componentSupplier.getMemberFinder().findOne(
				MethodCriteria.create()
				.name((name) -> name.matches("staticRun")).and().parameterTypes(params -> params.length == 0),
				Service.class				
			);
			Runnable virtualObj = componentSupplier.getFunctionalInterfaceFactory().create(mth);
			virtualObj.run();
		});
	}
}
