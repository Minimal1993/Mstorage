/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypt;

public class Simple extends SimpleAbstract {
    protected String Protected = "fromSimple";
    public String Public = "publicFromSimple";
    
    public Simple(){
        super();
        
//        System.out.println("Simple:" + this.Protected);
//        System.out.println("Simple:" + this.Public);
        
        this.methodFromConstructor();
    }
    
    @Override
    protected void methodFromConstructor(){
//        super.methodFromConstructor();
        
        System.out.println("Simple.methodFromConstructor:" + this.Protected);
        System.out.println("Simple.methodFromConstructor:" + this.Public);
    }
    
    public void workWithoutConstructor(){
        super.Public = "settedFromSimple";
        
        this.methodFromConstructorNotOverrided();
    }
    
    
}
