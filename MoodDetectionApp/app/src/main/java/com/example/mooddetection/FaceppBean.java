package com.example.mooddetection;

import java.util.List;

/**
 * the class is to handle detection result
 */
public class FaceppBean {


    private String image_id;
    private String request_id;
    private int time_used;
    private List<FacesBean> faces;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public List<FacesBean> getFaces() {
        return faces;
    }

    public void setFaces(List<FacesBean> faces) {
        this.faces = faces;
    }

    public static class FacesBean {

        private LandmarkBean landmark;
        private AttributesBean attributes;
        private FaceRectangleBean face_rectangle;
        private String face_token;

        public LandmarkBean getLandmark() {
            return landmark;
        }

        public void setLandmark(LandmarkBean landmark) {
            this.landmark = landmark;
        }

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public FaceRectangleBean getFace_rectangle() {
            return face_rectangle;
        }

        public void setFace_rectangle(FaceRectangleBean face_rectangle) {
            this.face_rectangle = face_rectangle;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public static class LandmarkBean {


            private MouthUpperLipLeftContour2Bean mouth_upper_lip_left_contour2;
            private ContourChinBean contour_chin;
            private RightEyePupilBean right_eye_pupil;
            private MouthUpperLipBottomBean mouth_upper_lip_bottom;

            public MouthUpperLipLeftContour2Bean getMouth_upper_lip_left_contour2() {
                return mouth_upper_lip_left_contour2;
            }

            public void setMouth_upper_lip_left_contour2(MouthUpperLipLeftContour2Bean mouth_upper_lip_left_contour2) {
                this.mouth_upper_lip_left_contour2 = mouth_upper_lip_left_contour2;
            }

            public ContourChinBean getContour_chin() {
                return contour_chin;
            }

            public void setContour_chin(ContourChinBean contour_chin) {
                this.contour_chin = contour_chin;
            }

            public RightEyePupilBean getRight_eye_pupil() {
                return right_eye_pupil;
            }

            public void setRight_eye_pupil(RightEyePupilBean right_eye_pupil) {
                this.right_eye_pupil = right_eye_pupil;
            }

            public MouthUpperLipBottomBean getMouth_upper_lip_bottom() {
                return mouth_upper_lip_bottom;
            }

            public void setMouth_upper_lip_bottom(MouthUpperLipBottomBean mouth_upper_lip_bottom) {
                this.mouth_upper_lip_bottom = mouth_upper_lip_bottom;
            }

            public static class MouthUpperLipLeftContour2Bean {

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class ContourChinBean {


                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RightEyePupilBean {


                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class MouthUpperLipBottomBean {

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }

        public static class AttributesBean {




            private EmotionBean emotion;



            public EmotionBean getEmotion() {
                return emotion;
            }

            public void setEmotion(EmotionBean emotion) {
                this.emotion = emotion;
            }










            public static class SmileBean {


                private double threshold;
                private double value;

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }
            }



            public static class EmotionBean {


                private double anger;
                private double disgust;
                private double fear;
                private double happiness;
                private double neutral;
                private double sadness;
                private double surprise;

                public double getAnger() {
                    return anger;
                }

                public void setAnger(double anger) {
                    this.anger = anger;
                }

                public double getDisgust() {
                    return disgust;
                }

                public void setDisgust(double disgust) {
                    this.disgust = disgust;
                }

                public double getFear() {
                    return fear;
                }

                public void setFear(double fear) {
                    this.fear = fear;
                }

                public double getHappiness() {
                    return happiness;
                }

                public void setHappiness(double happiness) {
                    this.happiness = happiness;
                }

                public double getNeutral() {
                    return neutral;
                }

                public void setNeutral(double neutral) {
                    this.neutral = neutral;
                }

                public double getSadness() {
                    return sadness;
                }

                public void setSadness(double sadness) {
                    this.sadness = sadness;
                }

                public double getSurprise() {
                    return surprise;
                }

                public void setSurprise(double surprise) {
                    this.surprise = surprise;
                }
            }
        }

        public static class FaceRectangleBean {


            private int width;
            private int top;
            private int left;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}

