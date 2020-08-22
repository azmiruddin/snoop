package test;

import javax.annotation.PostConstruct;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 27.03.19
 * TODO Description:
 */
public class AnnotationTest {


    String s;

    public AnnotationTest(String s){
        this.s = this.s + " " + s;
    }

    @PostConstruct
    private void init(){
        this.s = "init";
    }

    @Override
    public String toString(){
        return this.s;
    }

    public static void main(String[] args){

        AnnotationTest annotationTest = new AnnotationTest("Annotation Test");

        System.out.println(annotationTest.toString());
    }

}
