

/* First created by JCasGen Wed Aug 19 15:14:18 KST 2015 */
package log4j;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Aug 19 15:18:37 KST 2015
 * XML source: C:/Users/HentaiMaster/Documents/SpringWorkspace/my-uima/desc/TestDescriptor2.xml
 * @generated */
public class test extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(test.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected test() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public test(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public test(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public test(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: processName

  /** getter for processName - gets 
   * @generated
   * @return value of the feature 
   */
  public String getProcessName() {
    if (test_Type.featOkTst && ((test_Type)jcasType).casFeat_processName == null)
      jcasType.jcas.throwFeatMissing("processName", "log4j.test");
    return jcasType.ll_cas.ll_getStringValue(addr, ((test_Type)jcasType).casFeatCode_processName);}
    
  /** setter for processName - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProcessName(String v) {
    if (test_Type.featOkTst && ((test_Type)jcasType).casFeat_processName == null)
      jcasType.jcas.throwFeatMissing("processName", "log4j.test");
    jcasType.ll_cas.ll_setStringValue(addr, ((test_Type)jcasType).casFeatCode_processName, v);}    
  }

    