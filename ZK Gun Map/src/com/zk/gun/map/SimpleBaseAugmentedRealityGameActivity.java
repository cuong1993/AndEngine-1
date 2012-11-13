package com.zk.gun.map;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;

public abstract class SimpleBaseAugmentedRealityGameActivity extends BaseAugmentedRealityGameActivity{
	// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		// ===========================================================
		// Constructors
		// ===========================================================

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		protected abstract void onCreateResources();
		protected abstract Scene onCreateScene();

		@Override
		public final void onCreateResources(final OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
			this.onCreateResources();

			pOnCreateResourcesCallback.onCreateResourcesFinished();
		}

		@Override
		public final void onCreateScene(final OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
			final Scene scene = this.onCreateScene();

			pOnCreateSceneCallback.onCreateSceneFinished(scene);
		}

		@Override
		public final void onPopulateScene(final Scene pScene, final OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
			pOnPopulateSceneCallback.onPopulateSceneFinished();
		}

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
}
