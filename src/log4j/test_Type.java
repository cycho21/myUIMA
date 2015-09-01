
/* First created by JCasGen Wed Aug 19 15:14:18 KST 2015 */
package log4j;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed Aug 19 15:18:37 KST 2015
 * @generated */
public class test_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (test_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = test_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new test(addr, test_Type.this);
  			   test_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new test(addr, test_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = test.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("log4j.test");
 
  /** @generated */
  final Feature casFeat_processName;
  /** @generated */
  final int     casFeatCode_processName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getProcessName(int addr) {
        if (featOkTst && casFeat_processName == null)
      jcas.throwFeatMissing("processName", "log4j.test");
    return ll_cas.ll_getStringValue(addr, casFeatCode_processName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setProcessName(int addr, String v) {
        if (featOkTst && casFeat_processName == null)
      jcas.throwFeatMissing("processName", "log4j.test");
    ll_cas.ll_setStringValue(addr, casFeatCode_processName, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public test_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_processName = jcas.getRequiredFeatureDE(casType, "processName", "uima.cas.String", featOkTst);
    casFeatCode_processName  = (null == casFeat_processName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_processName).getCode();

  }
}



    