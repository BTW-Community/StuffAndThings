package net.minecraft.src;

public class YYContainerLargeBasket extends FCContainerWithInventory
{
    private static final int m_iInvetoryRows = 3;
    private static final int m_iInvetoryColumns = 4;
    private static final int m_iContainerInventoryDisplayX = 53;
    private static final int m_iContainerInventoryDisplayY = 18;
    private static final int m_iPlayerInventoryDisplayX = 8;
    private static final int m_iPlayerInventoryDisplayY = 83;

    public YYContainerLargeBasket(IInventory var1, IInventory var2)
    {
        super(var1, var2, m_iInvetoryRows, m_iInvetoryColumns, m_iContainerInventoryDisplayX, m_iContainerInventoryDisplayY, m_iPlayerInventoryDisplayX, m_iPlayerInventoryDisplayY);
        var2.openChest();
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer var1)
    {
        super.onCraftGuiClosed(var1);
        this.m_containerInventory.closeChest();
    }
}
