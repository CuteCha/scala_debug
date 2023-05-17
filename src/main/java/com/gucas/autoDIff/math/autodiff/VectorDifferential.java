package com.gucas.autoDIff.math.autodiff;

import com.gucas.autoDIff.math.Field;



public interface VectorDifferential<X extends Field<X>, D> {

	public D diff(VariableVector<X> i_v);
}
