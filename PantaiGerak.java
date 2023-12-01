import processing.core.PApplet;

public class PantaiGerak extends PApplet {
    private int oceanColor;
    private float sunX, sunY;
    private int xs, ys;
    private int skyColor1, skyColor2;

    private float cloud1X, cloud1Y;
    private float cloud2X, cloud2Y;
    private float cloud3X, cloud3Y;

    private int numTrees = 10; // Number of trees
    private float[] treeX;
    private float[] treeY;

    private Car car1, car2;

    public void settings() {
        size(800, 600);
        oceanColor = color(0, 119, 190);
        skyColor1 = color(135, 206, 250);
        skyColor2 = color(255, 165, 0);

        sunX = -50;
        sunY = height / 6;

        cloud1X = 100;
        cloud1Y = 50;
        cloud2X = 300;
        cloud2Y = 80;
        cloud3X = 500;
        cloud3Y = 60;

        // Initialize tree positions
        treeX = new float[numTrees];
        treeY = new float[numTrees];
        for (int i = 0; i < numTrees; i++) {
            treeX[i] = i * 80;  // Spacing trees 80 pixels apart
            treeY[i] = height - 120;  // Adjust the Y position to be closer to the top
        }

        // Initialize cars
        car1 = new Car(100, height - 40, 2);
        car2 = new Car(500, height - 40, -2);
    }

    public void draw() {
        background(lerpColor(skyColor1, skyColor2, map(sunX, 50, width + 50, 0, 1)));
        drawBeach();
        drawSun();
        drawCloud(cloud1X, cloud1Y);
        drawCloud(cloud2X, cloud2Y);
        drawCloud(cloud3X, cloud3Y);

        // Draw road
        drawRoad();

        // Draw multiple trees
        for (int i = 0; i < numTrees; i++) {
            drawCoconutTree(treeX[i], treeY[i]);
        }

        car1.update();
        car2.update();

        car1.display();
        car2.display();

        moveSun();
        moveTree();
        updateCloudPosition();
    }

    private void drawBeach() {
        noStroke();

        // Draw the noise wave for the sea
        for (int x = 0; x < width; x += 5) {
            float noiseValue = noise((float) (x * 0.003), (float) (frameCount * 0.01));
            float yOffset = map(noiseValue, 0, 1, -20, 20);

            // Gunakan warna laut yang lebih lembut
            fill(0, 119, 190);
            rect(x, height - 100 + yOffset, 5, 100 - yOffset);

            // Draw the sand on the beach
            fill(231, 196, 150);
            rect(0, height - 70, width, 70);
        }
    }

    private void drawSun() {
        fill(255, 255, 0);
        float angle = radians((float) (frameCount * 0.5)); // Rotate the sun smoothly
        pushMatrix();
        translate(sunX, sunY);
        rotate(angle);
        ellipse(0, 0, 75, 75);
        popMatrix(); // Reset transformation matrix
    }

    private void drawCloud(float x, float y) {
        fill(255);
        ellipse(x, y, 80, 60);
        ellipse(x + 20, y - 10, 80, 60);
        ellipse(x + 40, y, 80, 60);
    }

    private void drawCoconutTree(float x, float y) {
        fill(139, 69, 19);
        float trunkWidth = 15; // Make the trunk narrower
        float trunkHeight = 60; // Make the trunk shorter
        rect(x - trunkWidth / 2, y, trunkWidth, trunkHeight);
        fill(34, 139, 34);
        triangle(x - 25, y, x, y - 60, x + 25, y);
        triangle(x - 30, y - 30, x, y - 90, x + 30, y - 30);
        triangle(x - 25, y - 60, x, y - 120, x + 25, y - 60);
    }

    private void drawRoad() {
        fill(100); // Dark gray color for the road
        rect(0, height - 50, width, 50);
    }

    private void moveSun() {
        sunX += 1;

        if (sunX > width + 50) {
            sunX = -50;
        }
    }

    private void moveTree() {
        // Trees are not swaying in this version
    }

    private void updateCloudPosition() {
        cloud1X += 0.5;
        cloud2X += 0.3;
        cloud3X += 0.7;

        if (cloud1X > width + 50) {
            cloud1X = -50;
        }
        if (cloud2X > width + 50) {
            cloud2X = -50;
        }
        if (cloud3X > width + 50) {
            cloud3X = -50;
        }
    }

    class Car {
        float x, y;   // Car position
        float speed;  // Car speed

        Car(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }

        void update() {
            x += speed;

            // Reset car position when it goes off the screen
            if (x > width + 50) {
                x = -50;
            } else if (x < -50) {
                x = width + 50;
            }
        }

        void display() {
            // Body
            fill(255, 0, 0);
            rect(x, y, 80, 30);

            // Roof (jendela)
            fill(150, 200, 255); // Light blue color for the roof (jendela)
            rect(x + 10, y - 20, 60, 20);

            // Windows (kaca jendela)
            fill(200, 200, 255); // Lighter blue color for windows (kaca jendela)
            rect(x + 20, y - 10, 20, 10);

            // Front and rear wheels (roda depan dan belakang)
            float wheelRadius = 10;
            fill(0); // Black color for the wheels
            ellipse(x + 20, y + 30, wheelRadius * 2, wheelRadius * 2);
            ellipse(x + 60, y + 30, wheelRadius * 2, wheelRadius * 2);
        }
    }

    public static void main(String[] args) {
        PApplet.main("PantaiGerak");
    }
}