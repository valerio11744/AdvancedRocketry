package zmaster587.advancedRocketry.block;

import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlockTorchUnlitWall extends BlockTorchUnlit {

	   public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
	   private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.makeCuboidShape(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.makeCuboidShape(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

	   public BlockTorchUnlitWall(AbstractBlock.Properties p_i241193_1_) {
	      super(p_i241193_1_);
	      this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
	   }

	   /**
	    * Returns the unlocalized name of the block with "tile." appended to the front.
	    */
	   public String getTranslationKey() {
	      return this.asItem().getTranslationKey();
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return func_220289_j(state);
	   }

	   public static VoxelShape func_220289_j(BlockState p_220289_0_) {
	      return SHAPES.get(p_220289_0_.get(HORIZONTAL_FACING));
	   }

	   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		   return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
	   }
	   
	   @Nullable
	   public BlockState getStateForPlacement(BlockItemUseContext context) {
	      BlockState blockstate = this.getDefaultState();
	      IWorldReader iworldreader = context.getWorld();
	      BlockPos blockpos = context.getPos();
	      Direction[] adirection = context.getNearestLookingDirections();

	      for(Direction direction : adirection) {
	         if (direction.getAxis().isHorizontal()) {
	            Direction direction1 = direction.getOpposite();
	            blockstate = blockstate.with(HORIZONTAL_FACING, direction1);
	            if (blockstate.isValidPosition(iworldreader, blockpos)) {
	               return blockstate;
	            }
	         }
	      }

	      return null;
	   }

	   /**
	    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	    * returns its solidified counterpart.
	    * Note that this method should ideally consider only the specific face passed in.
	    */
	   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
	      return facing.getOpposite() == stateIn.get(HORIZONTAL_FACING) && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
	   }

	   /**
	    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	    * blockstate.
	    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
	    * fine.
	    */
	   public BlockState rotate(BlockState state, Rotation rot) {
	      return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
	   }

	   /**
	    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	    * blockstate.
	    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
	    */
	   public BlockState mirror(BlockState state, Mirror mirrorIn) {
	      return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	   }

	   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	      builder.add(HORIZONTAL_FACING);
	   }
}