package net.minecraft.src;

public class YYContainerLargeBasket extends FCContainerWithInventory{

	private static final int m_iInventoryRows = 3;
	private static final int m_iInventoryColumns = 4;
	
	private static final int m_iContainerInventoryDisplayX = 53;
	private static final int m_iContainerInventoryDisplayY = 18;
	
	private static final int m_iPlayerInventoryDisplayX = 8;
	private static final int m_iPlayerInventoryDisplayY = 83;
	
	public YYContainerLargeBasket(IInventory playerInventory, IInventory hamperInventory)
	{
		super( playerInventory, hamperInventory, m_iInventoryRows, m_iInventoryColumns, 
				m_iContainerInventoryDisplayX, m_iContainerInventoryDisplayY, 
				m_iPlayerInventoryDisplayX, m_iPlayerInventoryDisplayY );
			
		hamperInventory.openChest();
	}
	
	@Override
	public void onCraftGuiClosed( EntityPlayer player )
	{
		super.onCraftGuiClosed( player );
		
		m_containerInventory.closeChest();
	}
}
