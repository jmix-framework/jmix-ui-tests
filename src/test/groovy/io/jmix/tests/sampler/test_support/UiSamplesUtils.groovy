package io.jmix.tests.sampler.test_support

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

final class UiSamplesUtils {

    private UiSamplesUtils() {
    }

    public static final NumberFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
}
