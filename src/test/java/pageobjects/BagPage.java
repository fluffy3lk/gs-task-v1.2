package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

    private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
    private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
    public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
    public static final String REMOVE_ICON_ATTRIBUTE = "[data-locator-id^='miniBag-remove-";
    public static final String BAG_ITEM_ATTRIBUTE = "[data-locator-id^='miniBag-miniBagItem-";
    public static final String PRODUCT_PRICE_ATTRIBUTE = "[data-locator-id^='miniBag-price-";
    public static final String QUANTITY_TEXT_ATTRIBUTE = "-read']>div>div>div>div>[class^='qty-selector']>span";
    public static final By BAG_SUBTOTAL = By.cssSelector("[data-locator-id^='miniBag-subTotalValue-read']");
    public static final By EMPTY_BAG = By.cssSelector("div[class^='empty-view_empty-view__']");

    public BagPage() {
        getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
    }

    public List<Long> getVariantIdsInBag() {
        return getBagItems().stream().map(this::getVariantId).collect(Collectors.toList());
    }

    private List<WebElement> getBagItems() {
        return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
    }

    private long getVariantId(WebElement bagItem) {
        return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
    }

    public void removeAllProductsFromBag() {
        List<Long> variantIds = getVariantIdsInBag();
        for (Long variantId : variantIds) {
            removeProductIdFromBag(variantId);
        }
    }

    public void removeProductIdFromBag(Long productId) {
        getCommands().waitForAndClickOnElementLocated(By.cssSelector(REMOVE_ICON_ATTRIBUTE + productId.toString()));
    }

    public void changeQuantityByProductIdInBag(Long productId, int quantity) {
        try {
            getCommands().waitForElementToBeClickableLocated(By.cssSelector(BAG_ITEM_ATTRIBUTE + productId + QUANTITY_TEXT_ATTRIBUTE)).getText();
            getCommands().waitForAndClickOnElementLocated(By.cssSelector("[data-locator-id^='miniBag-quantityDropdown-" + productId + "-select'] > option[value='" + quantity + "']"));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProductPrice(Long productId) {
        return getCommands().waitForAndGetVisibleElementLocated(By.cssSelector(PRODUCT_PRICE_ATTRIBUTE + productId)).getText();
    }

    public int getProductQuantity(Long productId) {
        String textValue = getCommands().waitForElementToBeClickableLocated(By.cssSelector(BAG_ITEM_ATTRIBUTE + productId + QUANTITY_TEXT_ATTRIBUTE)).getText();
        return getNumericValue(textValue);
    }

    public int getProductSubTotalPriceByQuantity(Long productId) {
        String productPriceValue = getProductPrice(productId);
        int quantity = getProductQuantity(productId);
        int productPrice = getNumericValue(productPriceValue);

        int productSubtotal = quantity * productPrice;
        return productSubtotal;
    }

    public int getSubTotalPrice() {
        String subTotalPriceValue = getCommands().waitForAndGetVisibleElementLocated(BAG_SUBTOTAL).getText();
        return getNumericValue(subTotalPriceValue);

    }

    public boolean checkBagIsEmpty() {
        WebElement emptyBagElement = getCommands().waitForAndGetVisibleElementLocated(EMPTY_BAG);
        return emptyBagElement != null && emptyBagElement.isDisplayed();
    }

    public int getNumericValue(String textValue) {
        String numericValue = textValue.replaceAll("[^\\d]", "");
        return Integer.parseInt(numericValue);
    }

}

