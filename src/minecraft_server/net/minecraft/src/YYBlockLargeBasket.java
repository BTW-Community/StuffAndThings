package net.minecraft.src;

public class YYBlockLargeBasket extends FCBlockBasket
{
    public static final YYModelBlockLargeBasket m_model = new YYModelBlockLargeBasket();
    private boolean m_bRenderingBase = false;
    private Icon m_iconBaseOpenTop;
    private Icon m_iconFront;
    private Icon m_iconTop;
    private Icon m_iconBottom;

    protected YYBlockLargeBasket(int var1)
    {
        super(var1);
        this.setUnlocalizedName("yyBlockLargeBasket");
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new YYTileEntityLargeBasket();
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        if (!var1.isRemote && !FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(var1, var2, var3 + 1, var4, 0, true) && !FCUtilsWorld.IsBlockRestingOnThatBelow(var1, var2, var3 + 1, var4))
        {
        	YYTileEntityLargeBasket var10 = (YYTileEntityLargeBasket)var1.getBlockTileEntity(var2, var3, var4);

            if (var5 instanceof EntityPlayerMP)
            {
            	YYContainerLargeBasket var11 = new YYContainerLargeBasket(var5.inventory, var10);
                FCBetterThanWolves.ServerOpenCustomInterface((EntityPlayerMP)var5, var11, YYBaitMod.yyLargeBasketContainerID);
            }
        }

        return true;
    }

    public boolean HasCenterHardPointToFacing(IBlockAccess var1, int var2, int var3, int var4, int var5, boolean var6)
    {
        return var5 == 0 || var5 == 1 && !this.GetIsOpen(var1, var2, var3, var4);
    }

    public void OnCrushedByFallingEntity(World var1, int var2, int var3, int var4, EntityFallingSand var5)
    {
        if (!var1.isRemote)
        {
            this.DropItemsIndividualy(var1, var2, var3, var4, FCBetterThanWolves.fcItemWickerPiece.itemID, 2, 0, 0.75F);
        }
    }

    public FCModelBlock GetLidModel(int var1)
    {
        return m_model.m_lid;
    }

    public Vec3 GetLidRotationPoint()
    {
        return m_model.GetLidRotationPoint();
    }



    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return true;
    }


    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        AxisAlignedBB var6;

        if (this.GetIsOpen(var5))
        {
            var6 = m_model.m_selectionBoxOpen.MakeTemporaryCopy();
        }
        else
        {
            var6 = m_model.m_selectionBox.MakeTemporaryCopy();
        }

        var6.RotateAroundJToFacing(this.GetFacing(var5));
        var6.offset((double)var2, (double)var3, (double)var4);
        return var6;
    }
}
