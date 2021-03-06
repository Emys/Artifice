package shukaro.artifice.block.decorative;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import shukaro.artifice.ArtificeBlocks;
import shukaro.artifice.gui.ArtificeCreativeTab;
import shukaro.artifice.render.connectedtexture.ConnectedTextureBase;
import shukaro.artifice.render.connectedtexture.SolidConnectedTexture;
import shukaro.artifice.render.connectedtexture.TransparentConnectedTexture;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMarbleSlab extends BlockHalfSlab
{
    private final String[] types = { "marbleBrick", "marbleCobble", "marblePaver", "marbleAntipaver" };
    
    private Icon paverSide;
    
    private final Icon[][] textureList = ArtificeBlocks.blockMarble.getTextureList();
    
    private ConnectedTextureBase slabRenderer;
    private ConnectedTextureBase doubleRenderer;
    
    private final boolean isDouble;
    
    public BlockMarbleSlab(int id, boolean isDouble)
    {
        super(id, isDouble, Material.rock);
        setCreativeTab(ArtificeCreativeTab.tab);
        setLightOpacity(0);
        setHardness(1.5F);
        this.isDouble = isDouble;
        if (!isDouble)
            slabRenderer = new TransparentConnectedTexture(id);
        else
            doubleRenderer = new SolidConnectedTexture(id);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        meta = meta & 7;
        
        if (meta == 0)
            return ArtificeBlocks.blockMarble.getIcon(side, 2);
        if (meta == 1)
            return ArtificeBlocks.blockMarble.getIcon(side, 1);
        if (meta == 2 || meta == 3)
        {
            if (side != 0 && side != 1)
                return paverSide;
            return ArtificeBlocks.blockMarble.getIcon(side, 3);
        }
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess block, int x, int y, int z, int side)
    {
        int meta = block.getBlockMetadata(x, y, z) & 7;
        
        if (meta == 0)
            return ArtificeBlocks.blockMarble.getIcon(side, 2);
        if (meta == 1)
            return ArtificeBlocks.blockMarble.getIcon(side, 1);
        if (meta == 2 || meta == 3)
        {
            if (side != 0 && side != 1)
                return paverSide;
            else
            {
                if (this.isDouble)
                    return textureList[3][doubleRenderer.getBlockTexture(block, x, y, z, side)];
                else
                    return textureList[3][slabRenderer.getBlockTexture(block, x, y, z, side)];
            }
        }
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg)
    {
        paverSide = reg.registerIcon("artifice:marble_slab");
    }
    
    @Override
    public int idDropped(int id, Random rand, int meta)
    {
        return ArtificeBlocks.blockMarbleSlab.blockID;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        if (id != ArtificeBlocks.blockMarbleDoubleSlab.blockID)
        {
            for (int i = 0; i < types.length; i++)
                list.add(new ItemStack(id, 1, i));
        }
    }
    
    @Override
    public String getFullSlabName(int i)
    {
        return "tile.artifice.slab." + types[i].toLowerCase();
    }
    
}