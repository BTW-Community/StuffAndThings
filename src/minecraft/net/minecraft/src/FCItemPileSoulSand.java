// FCMOD

package net.minecraft.src;

public class FCItemPileSoulSand extends Item
{
	public FCItemPileSoulSand( int iItemID )
	{
		super( iItemID );
		
		SetBellowsBlowDistance( 1 );
		SetFilterableProperties( m_iFilterable_Fine );
		
		setUnlocalizedName( "fcItemPileSoulSand" );
		
		setCreativeTab( CreativeTabs.tabMaterials );
	}
}
