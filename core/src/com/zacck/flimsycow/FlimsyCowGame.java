package com.zacck.flimsycow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlimsyCowGame extends ApplicationAdapter {
	SpriteBatch batch;
	//a texture is an image
	Texture mScreenBackground;
	Texture mCow;
	
	//happens when the app is run
	@Override
	public void create () {
		batch = new SpriteBatch();
		mScreenBackground = new Texture("bg.png");
		mCow = new Texture("bird.png");

	}

	//loops throughout runtime of application
	@Override
	public void render () {
		//start displaying sprites
		batch.begin();
		//display the sprite background as a full background
		batch.draw(mScreenBackground, 0/* x corrdinate*/, 0/*y cordinate*/, Gdx.graphics.getWidth()/*gets full screen width*/, Gdx.graphics.getHeight()/*gets full screen height*/);
		//draw bird in the middle of the screen
		batch.draw(mCow,(Gdx.graphics.getWidth()/2)- mCow.getWidth()/2,(Gdx.graphics.getHeight()/2) - mCow.getHeight()/2);
		batch.end();

	}
}
