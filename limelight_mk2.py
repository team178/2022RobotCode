#imports used
import cv2
import numpy as np
#Written By Varun Chamarty & Jonathan Le

# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    # convert the input image to the HSV color space
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    
    # that do not fall within the following HSV Min/Max values(min(H,S,V), (max(H,S,V)))
    #img_threshold = cv2.inRange(img_hsv, (78, 39, 48), (90, 237, 244))
    img_threshold = cv2.inRange(img_hsv, (50, 100, 100), (120, 260, 200))
                                        #Make Adjustments here, left side is the minimum values, right side is the maximum values, adjust accordingly (Hue,Saturation,Brightness Value)
    # find contours in the new image
    contours, _ = cv2.findContours(img_threshold,
    cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

  
       
    # initialize an empty array of values to send back to the robot
    llpython = [0,0,0,0,0,0,0,0]
    boxes = []
    returnContour = np.array([[]])
    # if contours have been detected, draw them
    if len(contours) > 0:
   
        cv2.drawContours(image, contours, -1, 255, 2)
        
        
        for c in contours:#for loop
            
            (x, y, w, h) = cv2.boundingRect(c)
            boxes.append([x,y, x+w,y+h])
        
        listofCoordinates = np.asarray(boxes)
        
        left, top = np.min(listofCoordinates, axis=0)[:2]
       #finding the top left most value within given threshold
    
        right, bottom = np.max(listofCoordinates, axis=0)[2:]
        #finding the bottom right vaues within given threshold
        
        returnContour = [[[(left+right)/(2.0),(top+bottom)/(2.0)]]]
       #Find the average of both to find the center of the given rectangle(should work for any size rectangle)
    
        cv2.rectangle(image, (left, top), (right, bottom), (0, 0,255), 2)
        #This takes the image, take the two points given to it (leftx,topy) 
        #and (rightx,bottomy) and draws the rectangle:Numbers are for color(BGR) and line thickness respectively
      
        # record some custom data to send back to the robot
        llpython = [1,x,y,w,h,9,8,7]

    #return the largest contour for the LL crosshair, the modified image, and custom robot data
    return returnContour, image, llpython 
