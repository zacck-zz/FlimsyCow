package com.zacck.flimsycow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import sun.rmi.runtime.Log;


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


	//movement vars
	float cowY = 0; //how high the bird is
	float cowVelocity  = 0;

	int gameState = 0;
	//this is the space between the tube
	float mGap = 400;
	float maxTubeOffset;
	Random mrandomGenerator;
	//define number of tubes
	int mTubeNum = 4;
	//tubevelocity speed at which tubes move across the screen
	float mtubeVelocity = 4;
	//array of tubue horizontal positions
	float[] mtubeXPosition = new float[mTubeNum];
	//distance tubes move up and down
	float[] mTubeOffset = new float[4];
	//distance between tubes
	float mTubeDistance;

	//shapes for collision detections
	Circle mCowCircle = new Circle();
	ShapeRenderer mCowShapeRenderer;
	Rectangle[] mTopTubeRectangles;
	Rectangle[] mBotomTubeRectangles;

	//animation variables
	Animation cowAnimation;
	TextureRegion[] cowFrames;
	TextureRegion mFrame;
	float animInterval;







	//happens when the app is run
	@Override
	public void create () {
		//all our sprites
		batch = new SpriteBatch();
		mScreenBackground = new Texture("bg.png");
		mCowShapeRenderer = new ShapeRenderer();
		mrandomGenerator = new Random();

		//init array of cows
		mCows = new Texture[2];
		mCows[0] = new Texture("bird.png");
		mCows[1] = new Texture("bird2.png");
		//init tubes
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		//lets move them tubes randomly
		/*
		This sets the max movement up and down for the tubes so that it can only go
		half of the screens height up but accounting for the gap and accounting for
		stubby part of the tube
		 */
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - mGap / 2 - 100;
		//setup distance of tubes
		mTubeDistance = Gdx.graphics.getWidth() * 3/4;
		mTopTubeRectangles = new Rectangle[mTubeNum];
		mBotomTubeRectangles = new Rectangle[mTubeNum];
		//setup our 4 tubes
		for(int i =0; i < mTubeNum; i++)
		{
			//reset tube horizontal position to check the tube is in the middle
			mtubeXPosition[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * mTubeDistance;//make the first one in the middle then multiply by i to move the tubes away
			mTubeOffset[i] = (mrandomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - mGap - 200);

			//define rectangles for collision detection
			mTopTubeRectangles[i] = new Rectangle();
			mBotomTubeRectangles[i] = new Rectangle();


		}



		//set pos of cow
		cowY = (Gdx.graphics.getHeight()/2)-(mCows[flapState].getHeight()/2);
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
			}
			//lets move the tubes horizontally in a controlled random way
			for(int i =0; i < mTubeNum; i++) {

				//this checks if the position of the tube is completely off the screen at which we will push it to the other side
				if(mtubeXPosition[i] < -topTube.getWidth())
				{
					//we willl move the tube to the right by the distance between the tubes times 4 so as to make it appear after the 4th tube
					mtubeXPosition[i] += mTubeDistance * mTubeNum;
					//reset offset of tube when it moves to the other side of the screen
					mTubeOffset[i] = (mrandomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - mGap - 200);
				}
				else
				{
					//if not off thew edge of the screen we will move it as planned
					mtubeXPosition[i] = mtubeXPosition[i] - mtubeVelocity;
				}
				//randomly move the tubes up and down while maintaining enough space for the bird to go through using the max offset
				batch.draw(topTube, mtubeXPosition[i], Gdx.graphics.getHeight() / 2 + mGap / 2 + mTubeOffset[i]/*if width and height arent specified use the defaults*/);
				batch.draw(bottomTube, mtubeXPosition[i], Gdx.graphics.getHeight() / 2 - mGap / 2 - bottomTube.getHeight() + mTubeOffset[i]);

				//shapes or collision detection
				mTopTubeRectangles[i] = new Rectangle(mtubeXPosition[i],Gdx.graphics.getHeight() / 2 + mGap / 2 + mTubeOffset[i],topTube.getWidth(), topTube.getHeight());
				mBotomTubeRectangles[i] = new Rectangle(mtubeXPosition[i], Gdx.graphics.getHeight() / 2 - mGap / 2 - bottomTube.getHeight() + mTubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());
			}



			//stop the bird going off the screen
			if(cowY > 0 || cowVelocity < 0)
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


		//collision detection
		//mCowShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//mCowShapeRenderer.setColor(Color.BLUE);
		mCowCircle.set(Gdx.graphics.getWidth() / 2, cowY + mCows[flapState].getHeight() / 2, mCows[flapState].getWidth() / 2);
		//mCowShapeRenderer.circle(mCowCircle.x, mCowCircle.y, mCowCircle.radius);

		//display the rectanglwa
		for(int i =0; i < mTubeNum; i++ )
		{
			//mCowShapeRenderer.rect(mtubeXPosition[i],Gdx.graphics.getHeight() / 2 + mGap / 2 + mTubeOffset[i],topTube.getWidth(), topTube.getHeight());
			//mCowShapeRenderer.rect(mtubeXPosition[i], Gdx.graphics.getHeight() / 2 - mGap / 2 - bottomTube.getHeight() + mTubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());
			//detect collision
			if(Intersector.overlaps(mCowCircle,mTopTubeRectangles[i]) || Intersector.overlaps(mCowCircle,mBotomTubeRectangles[i]))
			{
				Gdx.app.log("Collission ", "Detected");
			}

		}

		//mCowShapeRenderer.end();





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
