package com.zacck.flimsycow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;


public class FlimsyCowGame extends ApplicationAdapter {
	SpriteBatch batch;

	//tube textures
	Texture topTube;
	Texture bottomTube;
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

	//movement vars
	float cowY = 0; //how high the bird is
	float cowVelocity  = 0;

	int gameState = 0;
	float mGap = 400;
	float maxTubeOffset;
	Random mrandomGenerator;
	float mTubeOffset;

	
	//happens when the app is run
	@Override
	public void create () {
		batch = new SpriteBatch();
		mScreenBackground = new Texture("bg.png");

		//init tubes
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		//init array of cows
		mCows = new Texture[2];
		mCows[0] = new Texture("bird.png");
		mCows[1] = new Texture("bird2.png");
		//set pos of cow
		cowY = (Gdx.graphics.getHeight()/2)-(mCows[flapState].getHeight()/2);


		///control animation speed of sprite
		cowFrames = new TextureRegion[2];
		//frames for both sprites
		cowFrames[0] = new TextureRegion(mCows[0]);
		cowFrames[1] = new TextureRegion(mCows[1]);
		//control speed of frame switching
		cowAnimation = new Animation(0.2f,cowFrames); //this is what we use to control the framerate

		//lets move them tubes randomly
		/*
		This sets the max movement up and down for the tubes so that it can only go
		half of the screens height up but accounting for the gap and accounting for
		stubby part of the tube
		 */
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - mGap / 2 - 100;
		mrandomGenerator = new Random();



	}

	//loops throughout runtime of application
	@Override
	public void render () {
		//draw bg
		batch.begin();
		//display the sprite background as a full background
		batch.draw(mScreenBackground, 0/* x corrdinate*/, 0/*y cordinate*/, Gdx.graphics.getWidth()/*gets full screen width*/, Gdx.graphics.getHeight()/*gets full screen height*/);


		//cow wont fall until user taps screen
		if(gameState != 0)
		{
			//this means we are playing the game
			//detect a tap on screen
			if(Gdx.input.justTouched())
			{
				//since velocity moves down if we negate the cow will pop up
				cowVelocity = -20;
				mTubeOffset = (mrandomGenerator.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - mGap - 200;

			}

			batch.draw(topTube, Gdx.graphics.getWidth()/2 - topTube.getWidth() / 2, Gdx.graphics.getHeight() / 2 + mGap / 2 + mTubeOffset/*if width and height arent specified use the defaults*/);
			batch.draw(bottomTube, Gdx.graphics.getWidth() / 2 - bottomTube.getWidth() / 2, Gdx.graphics.getHeight() / 2 - mGap / 2 - bottomTube.getHeight() + mTubeOffset);

			//stop the bird going off the screen
			if(cowY > 0/*this y position being the bottom of the screen */ || cowVelocity < 0)
			{
				//lets now use the tap velocity
				//make cow fall faster
				cowVelocity++;
				cowY -= cowVelocity;
			}



		}
		else
		{

			//detect a tap on screen
			if(Gdx.input.justTouched())
			{
				gameState = 1;
			}
		}
		//draw bird in the middle of the screen
		flap(flapState);
		//stop displaying sprites
		batch.end();





	}

	public void flap(int m)
	{
		//TODO study this code
		//batch.draw(mCows[m],(Gdx.graphics.getWidth()/2)- mCows[m].getWidth()/2,(Gdx.graphics.getHeight()/2) - mCows[m].getHeight()/2);
		animInterval += Gdx.graphics.getDeltaTime();
		mFrame = cowAnimation.getKeyFrame(animInterval, true);
		//lets draw the sprite in the middle of the screen
		batch.draw(mFrame,(Gdx.graphics.getWidth()/2)-(mCows[flapState].getWidth()/2), cowY);
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
