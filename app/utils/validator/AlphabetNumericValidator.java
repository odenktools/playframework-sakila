package utils.validator;

import play.data.validation.Constraints;
import play.libs.F;

/**
 * Alphanumeric Validator.
 * Created by odenktools@gmail.com on 01/12/2017.
 */
public class AlphabetNumericValidator extends Constraints.Validator<String> {

    @Override
    public boolean isValid(String value) {
        String pattern = "^[a-zA-Z0-9\\\\._\\\\-]+$";
        return value != null && value.matches(pattern);
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<String, Object[]>("only letters or number are allowed",
                new String[]{});
    }
}