package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefs {

    private final WebDriver driver;
    private Long productId;

    public ProductStepDefs() {
        this.driver = Hooks.getDriver();
    }

    @Given("the user is on a product page")
    public void theUserIsOnAProductPage() {
        driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
        productId = 39654522814667L;
        new ProductDisplayPage();
    }

    @When("adding the product to the Bag")
    public void addingTheProductToTheBag() {
        ProductDisplayPage productDisplayPage = new ProductDisplayPage();
        productDisplayPage.selectSmallSize();
        productDisplayPage.selectAddToBag();
    }

    @Then("the product should appear in the Bag")
    public void theProductShouldAppearInTheBag() {
        BagPage bagPage = new BagPage();
        List<Long> variantIds = bagPage.getVariantIdsInBag();
        assertThat(variantIds).as("Expected product is in Bag").contains(productId);
    }

    @When("remove all products from the Bag")
    public void removeAllProductsFromTheBag() {
        BagPage bagPage = new BagPage();
        bagPage.removeAllProductsFromBag();
    }

    @When("remove product {long} from the Bag")
    public void removeProductFromTheBag(Long productId) {
        BagPage bagPage = new BagPage();
        bagPage.removeProductIdFromBag(productId);
    }

    @When("increase product {long} quantity {int} in the Bag")
    public void increaseProductQuantityInTheBag(Long productId, int quantityIncrease) {
        BagPage bagPage = new BagPage();
        bagPage.changeQuantityByProductIdInBag(productId, quantityIncrease);
    }

    @When("decrease product {long} quantity {int} in the Bag")
    public void decreaseProductQuantityInTheBag(Long productId, int quantityDecrease) {
        BagPage bagPage = new BagPage();
        bagPage.changeQuantityByProductIdInBag(productId, quantityDecrease);
    }

    @Then("the product {long} should be removed from the Bag")
    public void theProductShouldBeRemovedFromTheBag(Long productId) {
        BagPage bagPage = new BagPage();
        bagPage.checkBagIsEmpty();
        assertThat(bagPage.checkBagIsEmpty()).as("The product removed from the bag").isTrue();
    }

    @Then("all products should be removed from the Bag")
    public void allProductsShouldBeRemovedFromTheBag() {
        BagPage bagPage = new BagPage();
        assertThat(bagPage.getVariantIdsInBag()).as("The product is removed from the bag").isEmpty();
    }

    @Then("quantity {int} of product {long} should be increased")
    public void quantityShouldBeIncreased(int quantityIncrease, Long productId) {
        BagPage bagPage = new BagPage();
        int productQuantity = bagPage.getProductQuantity(productId);
        System.out.println(productQuantity);
        assertThat(productQuantity).as("quantity is changed").isEqualTo(quantityIncrease);
    }

    @Then("quantity {int} of product {long} should be decreased")
    public void quantityShouldBeDecreased(int quantityDecrease, Long productId) {
        BagPage bagPage = new BagPage();
        int productQuantity = bagPage.getProductQuantity(productId);
        assertThat(productQuantity).as("quantity is changed").isEqualTo(quantityDecrease);
    }

    @Then("product {long} price should be changed")
    public void productPriceShouldBeChanged(Long productId) {
        BagPage bagPage = new BagPage();
        int actualProductPrice = bagPage.getProductSubTotalPriceByQuantity(productId);
        int subTotalPrice = bagPage.getSubTotalPrice();

        assertThat(actualProductPrice).as("Subtotal price is correct").isEqualTo(subTotalPrice);
    }


}
