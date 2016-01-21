package com.galas.filip.kropki.loading;

import java.util.List;

public interface Loadable {

	void setupFromParameters(EntityParameterMap parameters);

	List<EntityParameter<?>> getEntityParameters();

}
