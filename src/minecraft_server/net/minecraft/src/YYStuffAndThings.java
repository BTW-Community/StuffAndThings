package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class YYStuffAndThings extends FCAddOn
{
	public static YYBlockLargeBasket yyBlockLargeBasket;
	public static int yyLargeBasketContainerID = 240;
	public static Item yyBlockItemLargeBasket;
	
	public static YYItemBarkStitching yyItemBarkStitching;
	public static Item yyItemStitchedBark;

	public static Item yyItemBait;
	public static YYItemBaitMix yyItemBaitMix;
	
	public static Item yyItemBrokenBrick;
	public static YYBlockHempBrickWet yyBlockHempBrickWet;
	public static Item yyItemHempBrickWet;
	public static YYBlockHempBrickDry yyBlockHempBrickDry;
	public static Item yyItemHempBrickDry;
	
	public static Block yyBlockTorchSparklingUnlit;
	public static Block yyBlockTorchSparklingBurning;
	
	public static YYItemYarnSpinning yyItemYarnSpinning;
	public static Item yyItemDropSpindle;
	
	public static Block yyBlockTotemSapling;
	public static YYBlockTotem yyBlockTotem;
	public static YYBlockTotemBottom yyBlockTotemBottom;
	public static Block yyBlockTotemCarving;
	
	public static FCItemFood yyItemChickenDecaying;
	public static FCItemFood yyItemBeefDecaying;
	public static FCItemFood yyItemPorkDecaying;
	public static FCItemFood yyItemMuttonDecaying;
	public static FCItemFood yyItemFishDecaying;
	
	public static FCItemFood yyItemSpoiledMeat;
	public static FCItemFood yyItemCookedMeat;
	
	public YYStuffAndThings()
	{
		super("Stuff & Things", "1.2.0", "YYST");
	}
	
	private static Map<String, String> configOptions;
	
	@Override
	public void handleConfigProperties(Map<String, String> propertyValues) {
		configOptions = propertyValues;
		yyCreeperFire = Boolean.parseBoolean(configOptions.get("CreeperFire"));
		yyHCMeat = Boolean.parseBoolean(configOptions.get("HardcoreMeat"));
		
		hcsStartDistanceMultiplier = Double.parseDouble(configOptions.get("hcsStartDistanceMultiplier"));
		hcsNetherDistanceMultiplier = Double.parseDouble(configOptions.get("hcsNetherDistanceMultiplier"));
		hcsWitherDistanceMultiplier = Double.parseDouble(configOptions.get("hcsWitherDistanceMultiplier"));
		hcsEndDistanceMultiplier = Double.parseDouble(configOptions.get("hcsEndDistanceMultiplier"));
		
		hcsStartScatterMultiplier = Double.parseDouble(configOptions.get("hcsStartScatterMultiplier"));
		hcsNetherScatterMultiplier = Double.parseDouble(configOptions.get("hcsNetherScatterMultiplier"));
		hcsWitherScatterMultiplier = Double.parseDouble(configOptions.get("hcsWitherScatterMultiplier"));
		hcsEndScatterMultiplier = Double.parseDouble(configOptions.get("hcsEndScatterMultiplier"));
	}
	
	public String getConfigOption(String option)
	{
		return configOptions.get(option);
	}

	public static boolean yyCreeperFire;
	public static boolean yyHCMeat;
	
	public static double hcsStartDistanceMultiplier;
	public static double hcsNetherDistanceMultiplier;
	public static double hcsWitherDistanceMultiplier;
	public static double hcsEndDistanceMultiplier;
	
	public static double hcsStartScatterMultiplier;
	public static double hcsNetherScatterMultiplier;
	public static double hcsWitherScatterMultiplier;
	public static double hcsEndScatterMultiplier;
	
	@Override
	public void PreInitialize() {
		this.registerProperty("CreeperFire", "True", "Set the following to false to disable creepers setting things on fire, blowing up when damaged by fire, and dropping hellfire dust.");
		this.registerProperty("HardcoreMeat", "False", "Set the following to True to enable an unfinished and unsupported gamemode, Hardcore Meat.");
		
		this.registerProperty("hcsStartDistanceMultiplier", "1.0", "The following options influence HardcoreSpawn distances. The first four push the ring of highest respawn probability further from the original spawn. The second four make you more likely to spawn further away from the ring. The ones that apply are selected based on the progression milestones achieved in the world.");
		this.registerProperty("hcsNetherDistanceMultiplier", "2.0");
		this.registerProperty("hcsWitherDistanceMultiplier", "2.0");
		this.registerProperty("hcsEndDistanceMultiplier", "2.0");
		
		this.registerProperty("hcsStartScatterMultiplier", "1.0");
		this.registerProperty("hcsNetherScatterMultiplier", "1.0");
		this.registerProperty("hcsWitherScatterMultiplier", "2.0");
		this.registerProperty("hcsEndScatterMultiplier", "3.0");
	}
	
	@Override
	public void Initialize()
	{
		FCAddOnHandler.LogMessage("Initializing Stuff & Things Addon");
		
		anvilRestrictRecipes();
		reverseCE();
		
		//bowl nerf
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(Item.bowlEmpty, 4),
			new Object[] {
				"# #",
				" # ",
				'#', Block.planks
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.bowlEmpty),
			new Object[] {
				"# #",
				" # ",
				'#', Block.planks
		});
		
		// bait
		yyItemBait = new Item(26000)
			.SetBuoyant()
			.setUnlocalizedName("yyItemBait")
			.setCreativeTab(CreativeTabs.tabFood)
			.setMaxStackSize(16);
		yyItemBaitMix = new YYItemBaitMix(26001);
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemBaitMix, 1, YYItemBaitMix.maxDamage-1),
			new Object[] {
				new ItemStack(Item.bowlEmpty),
				new ItemStack(Item.rottenFlesh),
				new ItemStack(Item.dyePowder, 1, 15)
		});
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemBait, 6),
			new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemDogFood)
		});
		FCRecipes.AddCauldronRecipe(
			new ItemStack(FCBetterThanWolves.fcItemDogFood, 2),
			new ItemStack[] {
				new ItemStack(Item.rottenFlesh, 4),
				new ItemStack(Item.dyePowder, 4, 15),
				new ItemStack(Item.sugar, 1)
		});
		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
			new ItemStack(FCBetterThanWolves.fcItemDogFood, 2),
			new ItemStack[] {
				new ItemStack(Item.rottenFlesh, 4),
				new ItemStack(Item.dyePowder, 4, 15),
				new ItemStack(Item.sugar, 1)
		});
		
		
		// large basket
		yyBlockLargeBasket = new YYBlockLargeBasket(1500);
		TileEntity.addMapping(YYTileEntityLargeBasket.class, "yyLargeBasket");
		yyBlockItemLargeBasket = new ItemBlock(1500-256);
		FCRecipes.AddRecipe(
			new ItemStack(yyBlockLargeBasket, 1),
			new Object[] {
				"WWW",
				"MWM",
				"MSM",
				'W', new ItemStack(FCBetterThanWolves.fcItemWickerPiece),
				'M', new ItemStack(FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 32767),
				'S', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767)
		});
		
		// basket
		FCRecipes.RemoveVanillaShapelessRecipe(
			new ItemStack(FCBetterThanWolves.fcItemWickerPiece, 4),
			new Object[] {new ItemStack(FCBetterThanWolves.fcBlockBasketWicker)
		});
		yyItemBarkStitching = new YYItemBarkStitching(26002);
		yyItemStitchedBark = new Item(26003)
			.SetBuoyant()
			.setUnlocalizedName("yyItemStitchedBark")
			.SetBellowsBlowDistance(2)
			.SetFilterableProperties(Item.m_iFilterable_Thin)
			.SetIncineratedInCrucible()
			.SetFurnaceBurnTime(75);
		FCRecipes.AddShapelessRecipe(
				new ItemStack(FCBetterThanWolves.fcBlockBasketWicker),
				new Object[] {
					new ItemStack(yyItemStitchedBark),
					new ItemStack(yyItemStitchedBark),
					new ItemStack(yyItemStitchedBark),
					new ItemStack(yyItemStitchedBark)
		});
		
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemBarkStitching, 1, YYItemBarkStitching.maxDamage-1),
			new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata)
		});
		
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockBasketWicker),
			new Object[] {
				"##",
				"##",
				'#', FCBetterThanWolves.fcItemWickerPiece
		});
		
		
		// chest iron
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockChest),
			new Object[] {
				"###",
				"# #",
				"###",
				'#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767)
		});
		FCRecipes.AddRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockChest),
			new Object[] {
				"SSS",
				"SIS",
				"SSS",
				'S', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767),
				'I', new ItemStack(Item.ingotIron, 1)
		});
		Block.replaceBlock(FCBetterThanWolves.fcHopper.blockID, YYBlockHopper.class, this, new Object[]{});
		FCRecipes.AddStokedCrucibleRecipe(
			new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6),
			new ItemStack[] {new ItemStack(FCBetterThanWolves.fcBlockChest)}
		);
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcHopper),
			new Object[] {
				"# #",
				"XYX",
				" Z ",
				'#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767),
				'X', FCBetterThanWolves.fcItemGear,
				'Y', Block.pressurePlatePlanks,
				'Z', new ItemStack(FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, 32767)
		});
		FCRecipes.AddRecipe(
			new ItemStack(FCBetterThanWolves.fcHopper),
			new Object[] {
				"#C#",
				"XYX",
				" Z ",
				'#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767),
				'X', FCBetterThanWolves.fcItemGear,
				'Y', Block.pressurePlatePlanks,
				'Z', new ItemStack(FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, 32767),
				'C', new ItemStack(FCBetterThanWolves.fcBlockChest)
		});
		Block.replaceBlock(FCBetterThanWolves.fcBlockChest.blockID, YYBlockChest.class, this, new Object[]{});
		FCRecipes.AddStokedCrucibleRecipe(
			new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6),
			new ItemStack[] {new ItemStack(FCBetterThanWolves.fcHopper)}
		);
		
		// hamper
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockHamper),
			new Object[] {
				"###",
				"#P#",
				"###",
				'#', FCBetterThanWolves.fcItemWickerPiece,
				'P', Block.planks
		});
		FCRecipes.AddRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockHamper),
			new Object[] {
				"###",
				"# #", 
				"###",
				'#', FCBetterThanWolves.fcItemWickerPiece
		});
		FCRecipes.AddShapelessRecipe(
			new ItemStack(FCBetterThanWolves.fcItemWickerPiece, 4),
			new Object[] {new ItemStack(FCBetterThanWolves.fcBlockHamper)}
		);
		
		// chisel
		Block.replaceBlock(Block.oreDiamond.blockID, YYBlockOreDiamond.class, this, new Object[]{});
		Block.replaceBlock(Block.oreRedstone.blockID, YYBlockOreRedstone.class, this, new Object[]{false});
		Block.replaceBlock(Block.oreRedstoneGlowing.blockID, YYBlockOreRedstone.class, this, new Object[]{false});
		Block.oreDiamond.SetChiselsEffectiveOn(false);
		Block.oreRedstone.SetChiselsEffectiveOn(false);
		
		// light
		Block.torchRedstoneActive.setLightValue(0);
		Block.blockRedstone.setLightValue(0);
		
		// crops
		Block.replaceBlock(FCBetterThanWolves.fcBlockHempCrop.blockID, YYBlockHempCrop.class, this, new Object[] {});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"BC",
				" C",
				'A', FCBetterThanWolves.fcItemStone,
				'B', FCBetterThanWolves.fcItemHempFibers,
				'C', Item.stick
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"CB",
				"C ",
				'A', FCBetterThanWolves.fcItemStone,
					'B', FCBetterThanWolves.fcItemHempFibers,
				'C', Item.stick
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"BC",
				" C",
				'A', FCBetterThanWolves.fcItemStone,
				'B', Item.silk,
				'C', Item.stick
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"CB",
				"C ",
				'A', FCBetterThanWolves.fcItemStone,
				'B', Item.silk,
				'C', Item.stick
		});
		Item.hoeStone.setMaxDamage(12);
		
		// hardcore compass
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(Item.compass, 1),
			new Object[] {
				" # ",
				"#X#",
				" # ",
				'#', FCBetterThanWolves.fcItemNuggetIron,
				'X', Item.redstone
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.compass, 1),
			new Object[] {
				" # ",
				"#X#",
				" # ",
				'#', Item.ingotIron,
				'X', FCBetterThanWolves.fcItemSoulUrn
		});
		
		// brick oven recovery
		yyItemBrokenBrick = new Item(26004).setUnlocalizedName("yyItemBrokenBrick");
		yyBlockHempBrickWet = new YYBlockHempBrickWet(1503);
		yyItemHempBrickWet = new YYItemHempBrickWet(26005);
		TileEntity.addMapping(YYTileEntityHempBrickWet.class, "yyBrickHempWet");
		yyBlockHempBrickDry = new YYBlockHempBrickDry(1504);
		yyItemHempBrickDry = new YYItemHempBrickDry(26006);
		TileEntity.addMapping(YYTileEntityHempBrickDry.class, "yyBrickHempDry");
		FCRecipes.AddRecipe(
			new ItemStack(yyItemHempBrickDry),
			new Object[] {
				"FBF",
				"FFF",
				"FBF",
				'B', yyItemBrokenBrick,
				'F', FCBetterThanWolves.fcItemHempFibers
		});
		
		// torch stuff
		yyBlockTorchSparklingUnlit = new YYBlockTorchSparklingUnlit(1501);
		yyBlockTorchSparklingBurning = new YYBlockTorchSparklingBurning(1502);
		TileEntity.addMapping(YYTileEntityTorchSparkling.class, "yyTorchSparkling");
		Item.itemsList[yyBlockTorchSparklingUnlit.blockID] = new YYItemBlockTorchSparklingIdle(yyBlockTorchSparklingUnlit.blockID-256);
		Item.itemsList[yyBlockTorchSparklingBurning.blockID] = new YYItemBlockTorchSparklingBurning(yyBlockTorchSparklingBurning.blockID-256);
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyBlockTorchSparklingUnlit),
			new ItemStack[]{
				new ItemStack(Item.redstone),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit)
		});
		
		// armor balance
		((ItemArmor)FCBetterThanWolves.fcItemArmorWoolChest).damageReduceAmount++;
		Item.plateLeather.damageReduceAmount++;
		Item.legsLeather.damageReduceAmount++;
		((ItemArmor)FCBetterThanWolves.fcItemArmorTannedChest).damageReduceAmount++;
		((ItemArmor)FCBetterThanWolves.fcItemArmorTannedLeggings).damageReduceAmount++;
		
		// yarn
		yyItemDropSpindle = new Item(26008).setUnlocalizedName("yyItemDropSpindle").SetFilterableProperties(Item.m_iFilterable_Narrow);
		yyItemYarnSpinning = new YYItemYarnSpinning(26007);
		FCRecipes.AddRecipe(
			new ItemStack(yyItemDropSpindle),
			new Object[] {
				"P",
				"S",
				'P', FCBetterThanWolves.fcItemChiselWood,
				'S', FCBetterThanWolves.fcItemStone
		});
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemYarnSpinning, 1, YYItemYarnSpinning.maxDamage - 1),
			new ItemStack[] {
				new ItemStack(yyItemDropSpindle),
				new ItemStack(FCBetterThanWolves.fcItemWool, 1, FCUtilsInventory.m_iIgnoreMetadata)
		});
		
		
		// totem
		yyBlockTotemSapling = new YYBlockTotemSapling(1505);
		Item.itemsList[yyBlockTotemSapling.blockID] = new ItemBlock(1505-256);
		yyBlockTotem = new YYBlockTotem(1506);
		Item.itemsList[yyBlockTotem.blockID] = new ItemBlock(1506-256);
		yyBlockTotemBottom = new YYBlockTotemBottom(1507);
		Item.itemsList[yyBlockTotemBottom.blockID] = new ItemBlock(1507-256);
		yyBlockTotemCarving = new YYBlockTotemCarving(1508);
		Item.itemsList[yyBlockTotemCarving.blockID] = new ItemBlock(1508-256);
		
		// hardcore meat
		yyItemCookedMeat = new FCItemFood(26009, 4, 0.25F, true, "yyItemCookedMeat", true);
		yyItemSpoiledMeat = new FCItemFood(26010, 3, 0.25F, true, "yyItemSpoiledMeat", true);
		yyItemSpoiledMeat.setPotionEffect(Potion.hunger.id, FCItemFood.m_iFoodPoisioningIncreasedDuration, 0, 0.7F);
		FCRecipes.AddShapelessRecipe(
			new ItemStack( FCBetterThanWolves.fcItemMeatCured ),
			new Object[] {
				new ItemStack(yyItemSpoiledMeat),
				new ItemStack(FCBetterThanWolves.fcItemNitre
		)});
		FCRecipes.AddCampfireRecipe(yyItemSpoiledMeat.itemID, new ItemStack(yyItemCookedMeat));
		FurnaceRecipes.smelting().addSmelting(yyItemSpoiledMeat.itemID, new ItemStack(yyItemCookedMeat), 0);

		yyItemChickenDecaying = new FCItemFood(26011, FCItemFood.m_iChickenRawHungerHealed, FCItemFood.m_fChickenSaturationModifier, true, "yyItemChickenDecaying", true).SetStandardFoodPoisoningEffect();
		yyItemBeefDecaying = new FCItemFood( 26012, FCItemFood.m_iBeefRawHungerHealed, FCItemFood.m_fBeefSaturationModifier, true, "yyItemBeefDecaying", true).SetStandardFoodPoisoningEffect();
		yyItemMuttonDecaying = new FCItemFood(26013, FCItemFood.m_iMuttonRawHungerHealed, FCItemFood.m_fMuttonSaturationModifier, true, "yyItemMuttonDecaying", true).SetStandardFoodPoisoningEffect();
		yyItemFishDecaying = new FCItemFood(26014, FCItemFood.m_iFishRawHungerHealed, FCItemFood.m_fFishSaturationModifier, false, "yyItemFishDecaying").SetStandardFoodPoisoningEffect();
		yyItemPorkDecaying = new FCItemFood(26015, FCItemFood.m_iPorkChopRawHungerHealed, FCItemFood.m_fPorkChopSaturationModifier, true, "yyItemPorkDecaying", true).SetStandardFoodPoisoningEffect();
		
		FurnaceRecipes.smelting().addSmelting(yyItemPorkDecaying.itemID, new ItemStack(Item.porkCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemBeefDecaying.itemID, new ItemStack(Item.beefCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemChickenDecaying.itemID, new ItemStack(Item.chickenCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemFishDecaying.itemID, new ItemStack(Item.fishCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemMuttonDecaying.itemID, new ItemStack(FCBetterThanWolves.fcItemMuttonCooked), 0.35F);
		
		FCRecipes.AddCampfireRecipe(yyItemPorkDecaying.itemID, new ItemStack(Item.porkCooked));
		FCRecipes.AddCampfireRecipe(yyItemBeefDecaying.itemID, new ItemStack(Item.beefCooked));
		FCRecipes.AddCampfireRecipe(yyItemChickenDecaying.itemID, new ItemStack(Item.chickenCooked));
		FCRecipes.AddCampfireRecipe(yyItemFishDecaying.itemID, new ItemStack(Item.fishCooked));
		FCRecipes.AddCampfireRecipe(yyItemMuttonDecaying.itemID, new ItemStack(FCBetterThanWolves.fcItemMuttonCooked));
		
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemChickenDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemBeefDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemMuttonDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemFishDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemPorkDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		
		FCRecipes.AddStokedCauldronRecipe(
			new ItemStack( FCBetterThanWolves.fcItemTallow, 1 ),
			new ItemStack[] {new ItemStack(yyItemPorkDecaying, 1)});
		FCRecipes.AddStokedCauldronRecipe(
			new ItemStack( FCBetterThanWolves.fcItemTallow, 1 ),
			new ItemStack[] {new ItemStack(yyItemBeefDecaying, 4)});
		FCRecipes.AddStokedCauldronRecipe(
			new ItemStack( FCBetterThanWolves.fcItemTallow, 1 ),
			new ItemStack[] {new ItemStack(yyItemMuttonDecaying, 1)});
		
		if(yyHCMeat)
		{
			Item.porkRaw.setUnlocalizedName("yyItemPorkFresh");
			Item.beefRaw.setUnlocalizedName("yyItemBeefFresh");
			Item.chickenRaw.setUnlocalizedName("yyItemChickenFresh");
			Item.fishRaw.setUnlocalizedName("yyItemFishFresh");
			FCBetterThanWolves.fcItemMuttonRaw.setUnlocalizedName("yyItemMuttonFresh");
		}
		else
		{
			YYMeatDecay.stats.clear();
		}
				
		FCAddOnHandler.LogMessage("Stuff & Things Addon Initialization Complete");
	}
	
	
	public void anvilRestrictRecipe(ItemStack itemStack, Object aobj[] )
	{
		YYCraftingManagerAnvil.getInstance().addRecipe(itemStack, aobj);
		FCRecipes.RemoveVanillaRecipe(itemStack, aobj);
	}
	
	public void anvilRestrictRecipes()
	{
		anvilRestrictRecipe(new ItemStack(Item.pickaxeIron), new Object[] {
				"III",
				" S ",
				" S ",
				'I', Item.ingotIron,
				'S', Item.stick
				});
		anvilRestrictRecipe(new ItemStack(FCBetterThanWolves.fcItemChiselDiamond), new Object[] {
				"X", 
				'X', FCBetterThanWolves.fcItemIngotDiamond
			});
		anvilRestrictRecipe(new ItemStack(FCBetterThanWolves.fcItemShearsDiamond), new Object[] {
				"X ",
				" X",
				'X', FCBetterThanWolves.fcItemIngotDiamond
			});
		anvilRestrictRecipe( new ItemStack( Item.hoeDiamond ), new Object[] {
				"X#", 
				" #", 
				" #",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond			
			} );
		anvilRestrictRecipe( new ItemStack( FCBetterThanWolves.fcItemDiamondPlate, 1 ), new Object[] {
				"#X#",
				" Y ",
				'#', FCBetterThanWolves.fcItemStrap, 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemPadding
			} );
		anvilRestrictRecipe( new ItemStack( Item.helmetDiamond ), new Object[] {
				"XXX", 
				"XYX", 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemDiamondPlate
			} ); 
		anvilRestrictRecipe( new ItemStack( Item.plateDiamond ), new Object[] {
				"Y Y", 
				"XXX", 
				"XXX", 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemDiamondPlate
			} );
		anvilRestrictRecipe( new ItemStack( Item.legsDiamond ), new Object[] {
				"XXX", 
				"Y Y", 
				"Y Y", 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemDiamondPlate
			} );
		anvilRestrictRecipe( new ItemStack( Item.bootsDiamond ), new Object[] {
				"X X", 
				"X X", 
				'X', FCBetterThanWolves.fcItemIngotDiamond
			} );
		anvilRestrictRecipe( new ItemStack( Item.swordDiamond ), new Object[] {
				"X", 
				"X", 
				"#",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond
			} );
		anvilRestrictRecipe( new ItemStack( Item.pickaxeDiamond ), new Object[] {
				"XXX", 
				" # ", 
				" # ",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond			
			} ); 
		anvilRestrictRecipe( new ItemStack( Item.shovelDiamond ), new Object[] {
				"X", 
				"#", 
				"#",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond			
			} ); 
	}
	
	public void reverseCE()
	{
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth, 
				'X', new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
		});
		
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth, 
				'X', Block.planks 
		});
		FCRecipes.AddRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth, 
				'X', new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
		});
		FCRecipes.AddRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth,
				'X', Block.planks
		});
		
	}
}
