import java.util.InputMismatchException;
import java.util.Scanner;

public class ObjectCreator {

    private Object createSimpleObj() throws InputMismatchException {
        Scanner s = new Scanner(System.in); // Scanners are re initialized in each method to ensure clear buffer

        int i;
        boolean b;

        System.out.println("Enter an integer: ");
        i = s.nextInt();
        s.nextLine();

        System.out.println("Enter a boolean: ");
        b = s.nextBoolean();
        s.nextLine();

        return(new SimpleObject(i, b));
    }

    private Object createPrimArrayObj() throws InputMismatchException {
        Scanner s = new Scanner(System.in);
        int[] arr;
        int arrLength;

        System.out.println("Enter the length of the array: ");
        arrLength = s.nextInt();
        s.nextLine();
        arr = new int[arrLength];

        System.out.println("Enter elements to put in the array");
        for (int i = 0; i < arrLength; i++) {
            System.out.println("Enter element " + i);
            arr[i] = s.nextInt();
            s.nextLine();
        }
        return(new ArrayOfPrimsObject(arr));
    }

    private Object createObjRefObj() throws InputMismatchException {
        Scanner s = new Scanner(System.in);
        ObjectReferenceObject refObj;
        int input;

        System.out.println("Pick an object for the object to reference: ");
        System.out.println("1. Simple object with primitive fields");
        System.out.println("2. Simple object with an array of primitives");
        System.out.println("3. This object, creating a circular reference");

        input = s.nextInt();
        s.nextLine();

        if (input == 1) {
            refObj = new ObjectReferenceObject(createSimpleObj());
            return refObj;
        } else if (input == 2) {
            refObj = new ObjectReferenceObject(createPrimArrayObj());
            return refObj;
        } else if (input == 3) {
            refObj = new ObjectReferenceObject(new ObjectReferenceObject());
            return refObj;
        } else {
            System.out.println("Number out of range");
            System.exit(-1);
        }
        return null;
    }

    private Object[] createObjectArray() {
        Scanner s = new Scanner(System.in);
        Object[] objArray = new Object[0];
        int arrLength;
        int selection;

        System.out.println("Enter the length of the array: ");
        arrLength = s.nextInt();
        s.nextLine();

        System.out.println("Enter the type of objects in the array: ");
        System.out.println("1. Simple objects with primitive fields");
        System.out.println("2. Simple objects with an array of primitives");

        selection = s.nextInt();
        s.nextLine();

        if (selection == 1) {
            objArray = new SimpleObject[arrLength];
            for (int i = 0; i < arrLength; i++) {
                objArray[i] = createSimpleObj();
            }
        }
        else if (selection == 2) {
            objArray = new ArrayOfPrimsObject[arrLength];
            for (int i = 0; i < arrLength; i++) {
                objArray[i] = createPrimArrayObj();
            }
        }
        else {
            System.out.println("Number out of range");
            System.exit(-1);
        }
        return objArray;
    }

    private Object createArrayOfObjsObj() {
        Object[] objArray = createObjectArray();
        return new ArrayOfObjsObject(objArray);
    }

    private Object createCollectionObj() {
        Object[] objArray = createObjectArray();
        return new CollectionObject(objArray);
    }


    Object objectCreate() {
        Scanner s = new Scanner(System.in);
        int input = 0;

        System.out.println("Enter a number for the type of object to be created: ");
        System.out.println("1. Simple object with primitive fields");
        System.out.println("2. Simple object with an array of primitives");
        System.out.println("3. Object that references another object");
        System.out.println("4. Object with an array of objects");
        System.out.println("5. Object with a java collection object that references objects");

        try {
            input = s.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Input must be an int");
            System.exit(-1);
        }

        try {
            if (input == 1) {
                return createSimpleObj();
            } else if (input == 2) {
                return createPrimArrayObj();
            } else if (input == 3) {
                return createObjRefObj();
            } else if (input == 4) {
                return createArrayOfObjsObj();
            } else if (input == 5) {
                return createCollectionObj();
            } else {
                System.out.println("Number out of range");
            }
        }
        catch (InputMismatchException i) {
            System.out.println("Invalid input");
            System.exit(-1);
        }
        return null;
    }
}
