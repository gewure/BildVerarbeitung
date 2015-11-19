package imageanalyzer.datacontainers;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public enum JAIOperators {
    TRESHHOLD("threshold"),
    MEDIAN("median"),
    ERODE("erode"),
    DILATE("dilate"),
    INVERT("invert");

    private final String _operatorValue;

    JAIOperators(String operatorValue) {
        _operatorValue = operatorValue;
    }

    public String getOperatorValue() {
        return _operatorValue;
    }
}
