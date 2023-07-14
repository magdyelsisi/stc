package com.stc.filemanager.exception;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CustomDataFetcherExceptionResolverAdapter extends DataFetcherExceptionResolverAdapter {

	@Override
	protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
		return GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR).message(ex.getMessage())
				.path(env.getExecutionStepInfo().getPath()).location(env.getField().getSourceLocation()).build();
	}
}