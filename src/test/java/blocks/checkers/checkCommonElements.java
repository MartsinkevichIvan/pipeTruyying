package blocks.checkers;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface checkCommonElements {

     List<WebElement> getCommonWebElementsOfBlock();

    default <T extends Supplier<String>> checkCommonElements checkValues(T...values){
        Assert.assertEquals(getCommonWebElementsOfBlock().stream().map(x->x.getText()).collect(Collectors.toList()),
                Arrays.stream(values).map(Supplier::get).collect(Collectors.toList()));
        return this;
    }
}
