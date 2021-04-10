package net.minecraft.src;

public class YYItemHempBrickWet extends FCItemPlacesAsBlock
{
    public YYItemHempBrickWet(int var1)
    {
        super(var1, YYBaitMod.yyBlockHempBrickWet.blockID);
        this.m_bRequireNoEntitiesInTargetBlock = true;
        this.setUnlocalizedName("yyItemBrickHempWet");
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
}
