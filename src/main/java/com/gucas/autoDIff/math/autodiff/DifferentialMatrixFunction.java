package com.gucas.autoDIff.math.autodiff;

import com.gucas.autoDIff.math.Field;
import com.gucas.autoDIff.math.Ring;

public interface DifferentialMatrixFunction<X extends Field<X>> extends
		Ring<DifferentialMatrixFunction<X>>, Differential<X, DifferentialMatrixFunction<X>> {

}
