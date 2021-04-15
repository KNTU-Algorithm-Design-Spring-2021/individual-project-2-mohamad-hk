import java.awt.*;
import java.util.Scanner;

public class BoxStacking {
    static class Box {
        private int height;
        private int width;
        private int depth;
        private int weight;
        private Color colorFront;
        private Color colorBack;
        private Color colorLeft;
        private Color colorRight;
        private Color colorUp;
        private Color colorDown;

        public Box(int height, int width, int depth, int weight, Color colorFront, Color colorBack, Color colorLeft, Color colorRight, Color colorUp, Color colorDown) {
            this.height = height;
            this.width = width;
            this.depth = depth;
            this.weight = weight;
            this.colorFront = colorFront;
            this.colorBack = colorBack;
            this.colorLeft = colorLeft;
            this.colorRight = colorRight;
            this.colorUp = colorUp;
            this.colorDown = colorDown;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public int getDepth() {
            return depth;
        }

        public int getWeight() {
            return weight;
        }

        public Color getColorFront() {
            return colorFront;
        }

        public Color getColorBack() {
            return colorBack;
        }

        public Color getColorLeft() {
            return colorLeft;
        }

        public Color getColorRight() {
            return colorRight;
        }

        public Color getColorUp() {
            return colorUp;
        }

        public Color getColorDown() {
            return colorDown;
        }
    }

    private static void printMaxStackHeight(Box[] boxes, int boxCount) {
        int allBoxCount = boxCount * 6;
        Box[] allBoxRotations = new Box[allBoxCount];
        String[] boxMaxHeightOrder = new String[allBoxCount];

        for (int i = 0; i < boxCount; i++) {
            Box box = boxes[i];
            boxMaxHeightOrder[6 * i] = "#" + (i + 1);
            boxMaxHeightOrder[6 * i + 1] = "#" + (i + 1);
            boxMaxHeightOrder[6 * i + 2] = "#" + (i + 1);
            boxMaxHeightOrder[6 * i + 3] = "#" + (i + 1);
            boxMaxHeightOrder[6 * i + 4] = "#" + (i + 1);
            boxMaxHeightOrder[6 * i + 5] = "#" + (i + 1);

            allBoxRotations[6 * i] = new Box(box.getHeight(), box.getWidth(),
                    box.getDepth(), box.getWeight(), box.getColorFront(), box.getColorBack(), box.getColorLeft(), box.getColorRight(), box.getColorUp(), box.getColorDown());

            allBoxRotations[6 * i + 1] = new Box(box.getHeight(), box.getWidth(),
                    box.getDepth(), box.getWeight(), box.getColorFront(), box.getColorBack(), box.getColorRight(), box.getColorLeft(), box.getColorDown(), box.getColorUp());

            allBoxRotations[6 * i + 2] = new Box(box.getDepth(), box.getWidth(),
                    box.getHeight(), box.getWeight(), box.getColorFront(), box.getColorBack(), box.getColorUp(), box.getColorDown(), box.getColorRight(), box.getColorLeft());

            allBoxRotations[6 * i + 3] = new Box(box.getDepth(), box.getWidth(),
                    box.getHeight(), box.getWeight(), box.getColorFront(), box.getColorBack(), box.getColorDown(), box.getColorUp(), box.getColorLeft(), box.getColorRight());

            allBoxRotations[6 * i + 4] = new Box(box.getWidth(), box.getDepth(),
                    box.getHeight(), box.getWeight(), box.getColorRight(), box.getColorLeft(), box.getColorDown(), box.getColorUp(), box.getColorFront(), box.getColorBack());

            allBoxRotations[6 * i + 5] = new Box(box.getWidth(), box.getDepth(),
                    box.getHeight(), box.getWeight(), box.getColorLeft(), box.getColorRight(), box.getColorDown(), box.getColorUp(), box.getColorBack(), box.getColorFront());
        }

        for (int k = 0; k < allBoxCount; k++) {
            for (int j = k + 1; j < allBoxCount; j++) {
                Box tmp;
                String temp;
                if (allBoxRotations[k].getWeight() < allBoxRotations[j].getWeight()) {
                    tmp = allBoxRotations[k];
                    allBoxRotations[k] = allBoxRotations[j];
                    allBoxRotations[j] = tmp;
                    temp = boxMaxHeightOrder[k];
                    boxMaxHeightOrder[k] = boxMaxHeightOrder[j];
                    boxMaxHeightOrder[j] = temp;
                }
            }
        }

        int[] msh = new int[allBoxCount];
        int temp = 0;

        for (int i = 0; i < allBoxCount; i++) {
            msh[i] = 0;
            Box box = allBoxRotations[i];
            int val = 0;
            temp = -1;

            for (int j = 0; j < i; j++) {
                Box prevBox = allBoxRotations[j];
                if (((box.getWidth() < prevBox.getWidth() && box.getDepth() < prevBox.getDepth()) || (box.getDepth() < prevBox.getWidth() && box.getWidth() < prevBox.getDepth())) &&
                        (255 - box.getColorDown().getRed()) == prevBox.getColorUp().getRed() &&
                        (255 - box.getColorDown().getGreen()) == prevBox.getColorUp().getGreen() &&
                        (255 - box.getColorDown().getBlue()) == prevBox.getColorUp().getBlue()) {
                    if (val < msh[j]) {
                        val = msh[j];
                        temp = j;
                    }
                }
            }
            msh[i] = val + box.getHeight();
            if (temp != -1)
                boxMaxHeightOrder[i] = boxMaxHeightOrder[temp] + boxMaxHeightOrder[i];
        }

        int max = -1;

        for (int i = 0; i < allBoxCount; i++) {
            if (msh[i] > max) {
                max = msh[i];
                temp = i;
            }
        }

        System.out.println("\n\nMax stack height: " + max);
        System.out.println("Route: " + boxMaxHeightOrder[temp]);
    }

    public static void main(String[] args) {
        int cubeCount;
        System.out.print("Enter cubes count: ");
        Scanner scanner = new Scanner(System.in);
        cubeCount = scanner.nextInt();

        Box[] boxes = new Box[cubeCount];

        for (int i = 0; i < cubeCount; i++) {
            int height, width, depth, weight;
            String[] colorsFront, colorsBack, colorsLeft, colorsRight, colorsUp, colorsDown;
            System.out.println("\nBox #" + (i + 1) + ":");
            System.out.print("Enter height: ");
            height = scanner.nextInt();
            System.out.print("Enter width: ");
            width = scanner.nextInt();
            System.out.print("Enter depth: ");
            depth = scanner.nextInt();
            System.out.print("Enter weight: ");
            weight = scanner.nextInt();
            System.out.print("Enter front color in RGB format (255,255,255): ");
            colorsFront = scanner.next().split(",");
            System.out.print("Enter back color in RGB format (255,255,255): ");
            colorsBack = scanner.next().split(",");
            System.out.print("Enter left color in RGB format (255,255,255): ");
            colorsLeft = scanner.next().split(",");
            System.out.print("Enter right color in RGB format (255,255,255): ");
            colorsRight = scanner.next().split(",");
            System.out.print("Enter up color in RGB format (255,255,255): ");
            colorsUp = scanner.next().split(",");
            System.out.print("Enter down color in RGB format (255,255,255): ");
            colorsDown = scanner.next().split(",");

            boxes[i] = new Box(height, width, depth, weight,
                    new Color(Integer.parseInt(colorsFront[0].trim()), Integer.parseInt(colorsFront[1].trim()), Integer.parseInt(colorsFront[2].trim())),
                    new Color(Integer.parseInt(colorsBack[0].trim()), Integer.parseInt(colorsBack[1].trim()), Integer.parseInt(colorsBack[2].trim())),
                    new Color(Integer.parseInt(colorsLeft[0].trim()), Integer.parseInt(colorsLeft[1].trim()), Integer.parseInt(colorsLeft[2].trim())),
                    new Color(Integer.parseInt(colorsRight[0].trim()), Integer.parseInt(colorsRight[1].trim()), Integer.parseInt(colorsRight[2].trim())),
                    new Color(Integer.parseInt(colorsUp[0].trim()), Integer.parseInt(colorsUp[1].trim()), Integer.parseInt(colorsUp[2].trim())),
                    new Color(Integer.parseInt(colorsDown[0].trim()), Integer.parseInt(colorsDown[1].trim()), Integer.parseInt(colorsDown[2].trim())));
        }

        printMaxStackHeight(boxes, cubeCount);
    }
}