package com.zacck.flimsycow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class FlimsyCowGame extends ApplicationAdapter {
	SpriteBatch batch;
	//a texture is an image
	Texture mScreenBackground;
	//array of cows
	Texture[] mCows;
	int flapState = 0;

	//animation variables
	Animation cowAnimation;
	TextureRegion[] cowFrames;
	TextureRegion mFrame;
	float animInterval;
	
	//happens when the app is run
	@Override
	public void create () {
		batch = new SpriteBatch();
		mScreenBackground = new Texture("bg.png");
		//init array of cows
		mCows = new Texture[2];
		mCows[0] = new Texture("bird.png");
		mCows[1] = new Texture("bird2.png");

		///control animation speed of sprite
		cowFrames = new TextureRegion[2];
		//frames for both sprites
		cowFrames[0] = new TextureRegion(mCows[0]);
		cowFrames[1] = new TextureRegion(mCows[1]);
		//control speed of frame switching
		cowAnimation = new Animation(0.2f,cowFrames); //this is what we use to control the framerate


	}

	//loops throughout runtime of application
	@Override
	public void render () {

		//lets check what state the bird is in
			//start displaying sprites
			batch.begin();
			//display the sprite background as a full background
			batch.draw(mScreenBackground, 0/* x corrdinate*/, 0/*y cordinate*/, Gdx.graphics.getWidth()/*gets full screen width*/, Gdx.graphics.getHeight()/*gets full screen height*/);
			//draw bird in the middle of the screen
			flap(flapState);
			batch.end();



	}

	public void flap(int m)
	{
		//TODO study this code
		//batch.draw(mCows[m],(Gdx.graphics.getWidth()/2)- mCows[m].getWidth()/2,(Gdx.graphics.getHeight()/2) - mCows[m].getHeight()/2);
		animInterval += Gdx.graphics.getDeltaTime();
		mFrame = cowAnimation.getKeyFrame(animInterval, true);
		batch.draw(mFrame,(Gdx.graphics.getWidth()/2)-(mCows[flapState].getWidth()/2), (Gdx.graphics.getHeight()/2)-(mCows[flapState].getHeight()/2));
		if(m == 1)
		{
			flapState = 0;
		}
		else
		{
			flapState = 1;
		}
	}
}
