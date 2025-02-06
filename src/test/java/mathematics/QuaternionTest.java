package mathematics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuaternionTest {

   /** double numbers less than DELTA are considered zero */
   public static final double DELTA = 0.000001;

   @Test
   public void testConstructor() {
      Quaternion k1 = new Quaternion (-5., -81., 7., -13.);
      assertFalse(k1.isZero(), "constructor of -5-81i+7j-13k returns zero");
      Quaternion k2 = new Quaternion (0., 0., -1., 350.);
      assertNotEquals(k1.getRpart(), k2.getRpart(), 0.0, "different numbers must be different");
   }

   @Test
   public void testGetReIms() {
      Quaternion k1 = new Quaternion (3., 7., -5., -2.);
      double r = k1.getRpart();
      assertEquals (3., r, DELTA, "3+7i-5j-2k has wrong real part");
      r = k1.getIpart();
      assertEquals (7., r, DELTA, "3+7i-5j-2k has wrong imaginary part i");
      r = k1.getJpart();
      assertEquals (-5., r, DELTA, "3+7i-5j-2k has wrong imaginary part j");
      r = k1.getKpart();
      assertEquals (-2., r, DELTA, "3+7i-5j-2k has wrong imaginary part k");
      k1 = new Quaternion (0., 0., 0., 0.);
      r = k1.getRpart();
      assertEquals (0., r, DELTA, "zero has wrong real part");
   }

   @Test
   public void testMinusSign() {
      String quaternionToMatch = "-1-2i-3j-4k";
      Quaternion realQuaternion = new Quaternion(-1., -2., -3., -4.);
      assertEquals(Quaternion.valueOf(quaternionToMatch), realQuaternion);
   }

   // TODO
   @Test
   public void testMissingElements() {
      String quaternionToMatch = "-1-2i";
      Quaternion realQuaternion = new Quaternion(-1., -2., 0., 0.);
      assertEquals(Quaternion.valueOf(quaternionToMatch), realQuaternion);
   }

   // TODO
   @Test
   public void testOneRealElement() {
      String quaternionToMatch = "-1";
      Quaternion realQuaternion = new Quaternion(-1., 0., 0., 0.);
      assertEquals(Quaternion.valueOf(quaternionToMatch), realQuaternion);
   }

   // TODO
   @Test
   public void testOneImaginaryElement() {
      String quaternionToMatch = "i";
      Quaternion realQuaternion = new Quaternion(0., 1., 0., 0.);
      assertEquals(Quaternion.valueOf(quaternionToMatch), realQuaternion);
   }

   // TODO
   @Test
   public void testOnlyImaginaryElements() {
      String quaternionToMatch = "i-j-k";
      Quaternion realQuaternion = new Quaternion(0., 1., -1., -1.);
      assertEquals(Quaternion.valueOf(quaternionToMatch), realQuaternion);
   }

   @Test
   public void testSameElementTwice() {
      String quaternionString = "5+6i+7i+8k";
      assertThrows(RuntimeException.class, () -> Quaternion.valueOf(quaternionString));
   }

   // TODO
   @Test
   public void testElementsSwitched() {
      String quaternionString = "5+6j+7i+8k";
      assertThrows(RuntimeException.class, () -> Quaternion.valueOf(quaternionString));
   }

   @Test
   public void testIncorrectElement() {
      String quaternionString = "5+6ii+7i+8k";
      assertThrows(RuntimeException.class, () -> Quaternion.valueOf(quaternionString));
   }

   @Test
   public void testToString() {
      String s = new Quaternion (1., 4., 5., 7.).toString();
      assertTrue((s.indexOf('1') < s.indexOf('4')) && (s.indexOf('1') >= 0)
                      && (s.indexOf('4') < s.indexOf('5')) &&
                      (s.indexOf('5') < s.indexOf('7')), "String <" + s + "> does not represent 1+4i+5j+7k");
      s = new Quaternion (-1., 5., 0., 0.).toString();
      assertTrue(s.indexOf('-') >= 0, "String <" + s + "> does not contain a minus");
   }

   @Test
   public void testValueOf() {
      Quaternion f = new Quaternion (2., 5., 7., 9.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
      f = new Quaternion (-17., 10., 5., 8.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
      f = new Quaternion (0., 0., 0., 0.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
      f = new Quaternion (3., -2., 1., 3.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
      f = new Quaternion (-3., 0., -5., 1.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
      f = new Quaternion (3., 0., 0., -1.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
      f = new Quaternion (-3., 0., 0., 0.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
      f = new Quaternion (-3., -1., -10., -30.);
      assertEquals (f, Quaternion.valueOf (f.toString()), "valueOf must read back what toString outputs.");
   }

   @Test
   public void testEqualsClone() {
      Quaternion k1 = new Quaternion (3., 7., 5., 2.);
      Quaternion k2 = new Quaternion (3., -9., 5., 2.);
      Quaternion k3 = new Quaternion (3., 7., 5., 2.);
      assertEquals(k1, k3, "3+7i+5j+2k must be equal to 3+7i+5j+2k");
      assertNotEquals(k1, k2, "3+7i+5j+2k must not be equal to 3-9i+5j+2k");
      Quaternion k4 = null;
      try {
         k4 = (Quaternion)k3.clone();
      } catch (CloneNotSupportedException ignored) {}
      assertEquals(k3, k1, "independent instances of the same number must be equal");
      assertEquals(k3, k4, "clone must be equal to original");
      assertNotSame(k3, k4, "clone must not be identical to original");
      assertTrue(new Quaternion (0., 0., 0., 0.).isZero(), "zero must satisfy isZero");
      assertEquals(k2, new Quaternion(k2.getRpart(),
                    k2.getIpart(), k2.getJpart(), k2.getKpart()), "number created from parts must be equal to original");
      Quaternion k5 = new Quaternion (3.000000000000001, 7.000000000000001,
         5., 2.);
      assertEquals(k5, k1, "cannot compare real numbers using == operator");
   }

   @Test
   public void testIsZero() {
      Quaternion k1 = new Quaternion (0., 0., 0., 0.);
      assertTrue(k1.isZero(), "0+0i+0j+0k must be zero");
      k1 = new Quaternion (0., 1., 0., 0.);
      assertFalse(k1.isZero(), "0+1i+0j+0k must not be zero");
      k1 = new Quaternion (0., -0.1, 0., 0.);
      assertFalse(k1.isZero(), "0-0.1i+0j+0k must not be zero");
      k1 = new Quaternion (3., 7., 5., 2.);
      Quaternion k5 = new Quaternion (3.000000000000001, 7.000000000000001,
         5., 2.);
      System.out.println(k5.minus(k1));
      assertTrue(k5.minus(k1).isZero(), "cannot compare real numbers using == operator");
   }

   @Test
   public void testClone() {
      Quaternion f1 = new Quaternion (2., 5., 3., 7.);
      Quaternion f2 = null;
      try {
         f2 = (Quaternion)f1.clone();
      } catch (CloneNotSupportedException ignored) {}
      assertNotSame(f2, f1, "clone must differ from original");
      assertEquals(f1, f2, "clone must be equal to original");
      assertEquals(f2, f1, "clone must be equal to original");
      assert f2 != null;
      f2.plus(f2);
      assertEquals(new Quaternion (2., 5., 3., 7.),
              f2, "clone must be independent from original");
      f1 = new Quaternion (0., 0., 0., 0.);
      try {
         f2 = (Quaternion)f1.clone();
      } catch (CloneNotSupportedException ignored) {}
      assertNotSame(f2, f1, "clone must differ from original");
      assertEquals(f1, f2, "clone must be equal to original");
   }

   @Test
   public void testOpposite() {
      Quaternion f1 = new Quaternion (1., 6., 8., 10.);
      Quaternion f2 = f1.opposite();
      assertEquals(new Quaternion (-1., -6., -8., -10.), f2, "Wrong opposite: 1+6i+8j+10k");
      assertEquals(new Quaternion (1., 6., 8., 10.),
              f1, "Do not change the argument of opposite");
      f1 = new Quaternion (-4., -75., -45., -1.);
      f2 = f1.opposite();
      assertEquals(new Quaternion (4., 75., 45., 1.), f2, "Wrong opposite: -4-75i-45j-1k");
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = f1.opposite();
      assertEquals(f1, f2, "zero must be neutral to opposite");
   }

   @Test
   public void testConjugate() {
      Quaternion f1 = new Quaternion (1., 6., 7., 11.);
      Quaternion f2 = f1.conjugate();
      assertEquals(new Quaternion (1., -6., -7., -11.), f2, "Wrong conjugate: 1+6i+7j+11k");
      assertEquals(new Quaternion (1., 6., 7., 11.),
              f1, "Do not change the argument of conjugate");
      f1 = new Quaternion (-4., -75., -2., -3.);
      f2 = f1.conjugate();
      assertEquals(new Quaternion (-4., 75., 2., 3.), f2, "Wrong conjugate: -4-75i-2j-3k");
      f1 = new Quaternion (12340., 0., 0., 0.);
      f2 = f1.conjugate();
      assertEquals(f1, f2, "real number must be neutral to conjugate");
   }

   @Test
   public void testPlus() {
      Quaternion f1 = new Quaternion (2., 5., 1., 3.);
      Quaternion f2 = new Quaternion (4., 15., 2., 7.);
      Quaternion sum = f1.plus(f2);
      assertEquals(new Quaternion (6., 20., 3., 10.),
              sum, "Wrong sum: <" + f1 + "> + <" + f2 + ">");
      assertEquals(new Quaternion (2., 5., 1., 3.), f1,
              "plus must not change the first argument");
      assertEquals(new Quaternion (4., 15., 2., 7.), f2,
              "plus must not change the second argument");
      f1.plus (f1);
      assertEquals(new Quaternion (2., 5., 1., 3.), f1,
              "plus must not change the arguments!");
      f1 = new Quaternion (-1., 25., -2., 3.);
      f2 = new Quaternion (1., -25., 2., -3.);
      sum = f1.plus(f2);
      assertEquals(new Quaternion (0., 0., 0., 0.),
              sum, "Wrong sum: <" + f1 + "> + <" + f2 + ">");
      f1 = new Quaternion (1., -22., 3., -5.);
      f2 = new Quaternion (0., 0., 0., 0.);
      sum = f1.plus (f2);
      assertEquals(new Quaternion (1., -22., 3., -5.),
              sum, "Wrong sum: <" + f1 + "> + <" + f2 + ">");
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = new Quaternion (-1., 41., -6., 9.);
      sum = f1.plus (f2);
      assertEquals(new Quaternion (-1., 41., -6., 9.),
              sum, "Wrong sum: <" + f1 + "> + <" + f2 + ">");
      f1 = new Quaternion (-2., -5., -4., -7.);
      f2 = new Quaternion (-1., -6., -2., -3.);
      sum = f1.plus(f2);
      assertEquals(new Quaternion (-3., -11., -6., -10.),
              sum, "Wrong sum: <" + f1 + "> + <" + f2 + ">");
   }

   @Test
   public void testMinus() {
      Quaternion f1 = new Quaternion (2., 5., 1., 3.);
      Quaternion f2 = new Quaternion (4., 15., 2., 7.);
      Quaternion dif = f1.minus(f2);
      assertEquals(new Quaternion (-2., -10., -1., -4.),
              dif, "Wrong difference: <" + f1 + "> - <" + f2 + ">");
      assertEquals(new Quaternion (2., 5., 1., 3.), f1,
              "minus must not change the first argument");
      assertEquals(new Quaternion (4., 15., 2., 7.), f2,
              "minus must not change the second argument");
      dif = f1.minus (f1);
      assertEquals(new Quaternion (2., 5., 1., 3.), f1,
              "minus must not change the arguments!");
      assertEquals(new Quaternion (0., 0., 0., 0.),
              dif, "Wrong difference: <" + f1 + "> - <" + f1 + ">");
      f1 = new Quaternion (4., 25., 13., 8.);
      f2 = new Quaternion (1., 5., 7., 4.);
      dif = f1.minus(f2);
      assertEquals(new Quaternion (3., 20., 6., 4.),
              dif, "Wrong difference: <" + f1 + "> - <" + f2 + ">");
   }

   @Test
   public void testTimesQ() {
      Quaternion f1 = new Quaternion (2., 3., 5., 7.);
      Quaternion f2 = new Quaternion (19., 17., 13., 11.);
      Quaternion prd = f1.times (f2);
      assertEquals(new Quaternion (-155., 55., 207., 109.),
              prd, "Wrong product: <" + f1 + "> * <" + f2 + ">");
      assertEquals(new Quaternion (2., 3., 5., 7.),
              f1, "Do not change the first argument of times");
      assertEquals(new Quaternion (19., 17., 13., 11.),
              f2, "Do not change the second argument of times");
      prd = f2.times (f1);
      assertEquals(new Quaternion (-155., 127., 35., 201.),
              prd, "Wrong product: <" + f2 + "> * <" + f1 + ">");
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = new Quaternion (2., 3., 4., 5.);
      prd = f1.times (f2);
      assertEquals(new Quaternion (0., 0., 0., 0.),
              prd, "Wrong product: <" + f1 + "> * <" + f2 + ">");
      prd = f2.times (f1);
      assertEquals(new Quaternion (0., 0., 0., 0.),
              prd, "Wrong product: <" + f2 + "> * <" + f1 + ">");
      f1 = new Quaternion (1., 0., 0., 0.);
      f2 = new Quaternion (2., 3., 4., 5.);
      prd = f1.times (f2);
      assertEquals(new Quaternion (2., 3., 4., 5.),
              prd, "Wrong product: <" + f1 + "> * <" + f2 + ">");
      prd = f2.times (f1);
      assertEquals(new Quaternion (2., 3., 4., 5.),
              prd, "Wrong product: <" + f2 + "> * <" + f1 + ">");
   }

   @Test
   public void testTimesR() {
      Quaternion f = new Quaternion (2., 3., 5., 7.);
      Quaternion prd = f.times (0.5);
      assertEquals(new Quaternion (1., 1.5, 2.5, 3.5),
              prd, "Wrong product: <" + f + "> * 0.5");
      assertEquals(new Quaternion (2., 3., 5., 7.),
              f, "Do not change the first argument of times");
      prd = f.times (-20.);
      assertEquals(new Quaternion (-40., -60., -100., -140.),
              prd, "Wrong product: <" + f + "> * 20");
   }

   @Test
   public void testDotMult() {
      Quaternion f1 = new Quaternion (1., 2., 3., 5.);
      Quaternion f2 = new Quaternion (4., 7., -1., -6.);
      Quaternion prd = f1.dotMult (f2);
      assertEquals(new Quaternion (-15., 0., 0., 0.),
              prd, "Wrong dot product: <" + f1 + "> * <" + f2 + ">");
      assertEquals(new Quaternion (1., 2., 3., 5.),
              f1, "Do not change the first argument of dotMult");
      assertEquals(new Quaternion (4., 7., -1., -6.),
              f2, "Do not change the second argument of dotMult");
      prd = f2.dotMult (f1);
      assertEquals(new Quaternion (-15., 0., 0., 0.),
              prd, "Wrong dot product: <" + f2 + "> * <" + f1 + ">");
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = new Quaternion (2., 3., 4., 5.);
      prd = f1.dotMult (f2);
      assertEquals(new Quaternion (0., 0., 0., 0.),
              prd, "Wrong dot product: <" + f1 + "> * <" + f2 + ">");
      prd = f2.dotMult (f1);
      assertEquals(new Quaternion (0., 0., 0., 0.),
              prd, "Wrong product: <" + f2 + "> * <" + f1 + ">");
      f1 = new Quaternion (0., 2., 2., -3.);
      f2 = new Quaternion (0., 4., 2., 4.);
      prd = f1.dotMult (f2);
      assertEquals(new Quaternion (0., 0., 0., 0.),
              prd, "Wrong dot product: <" + f1 + "> * <" + f2 + ">");
      prd = f2.dotMult (f1);
      assertEquals(new Quaternion (0., 0., 0., 0.),
              prd, "Wrong dot product: <" + f2 + "> * <" + f1 + ">");
   }

   @Test
   public void testNorm() {
      Quaternion k1 = new Quaternion (4., 3., 0., 0.);
      double mm = k1.norm();
      assertEquals (5., mm, DELTA, "wrong module for 4+3i+0j+0k");
      k1 = new Quaternion (0., 0., 0., 0.);
      mm = k1.norm();
      assertEquals (0., mm, DELTA, "wrong module for zero");
      k1 = new Quaternion (1, -1., 1., 1.);
      mm = k1.norm();
      assertEquals (2., mm, DELTA, "wrong module for 1-1i+1j+1k");
      k1 = new Quaternion (0., 8., 0., 0.);
      mm = k1.norm();
      assertEquals (8., mm, DELTA, "wrong module for 0+8i+0j+0k");
   }

   @Test
   public void testInverse() {
      Quaternion f1 = new Quaternion (0.0032, -0.0048, -0.0096, -0.0384);
      Quaternion f2 = f1.inverse();
      assertEquals(new Quaternion (2., 3., 6., 24.), f2, "Wrong inverse <"+ f1 +">");
      assertEquals(new Quaternion (0.0032, -0.0048, -0.0096, -0.0384),
              f1, "Do not change the argument of inverse");
      f1 = f2.inverse();
      assertEquals(new Quaternion (0.0032, -0.0048, -0.0096, -0.0384), f1,
              "Wrong inverse: 2+3i+6j+24k");
      f1 = new Quaternion (1., 0., 0., 0.);
      f2 = f1.inverse();
      assertEquals(f1, f2, "1 must be neutral to inverse");
   }

   @Test
   public void testZeroInverse() {
      Quaternion f = new Quaternion (0., 0., 0., 0.);
      assertThrows(RuntimeException.class, f::inverse);
   }

   @Test
   public void testDivideByRight() {
      Quaternion f1 = new Quaternion (0.25, 0.25, -0.75, 0.5);
      Quaternion f2 = new Quaternion (1., -2., -1., 2.);
      Quaternion qt = f1.divideByRight (f2);
      assertEquals(new Quaternion (0.15, 0.175, 0.1, 0.175),
              qt, "Wrong quotient from divideByRight: <" + f1 + "> / <" + f2 + ">");
      assertEquals(new Quaternion (0.25, 0.25, -0.75, 0.5),
              f1, "Do not change the first argument of divideByRight");
      assertEquals(new Quaternion (1., -2., -1., 2.),
              f2, "Do not change the second argument of divideByRight");
      qt = f1.divideByRight (f1);
      assertEquals(new Quaternion (0.25, 0.25, -0.75, 0.5),
              f1, "Do not change the arguments of divideByRight");
      assertEquals(new Quaternion (1., 0., 0., 0.),
              qt, "Wrong quotient from divideByRight: <" + f1 + "> / <" + f1 + ">");
      f1 = new Quaternion (1., -1., 1., 1.);
      f2 = new Quaternion (1., 1., -1., 1.);
      qt = f1.divideByRight (f2);
      assertEquals(new Quaternion (0., -1., 0., 0.),
              qt, "Wrong quotient from divideByRight: <" + f1 + "> / <" + f2 + ">");
      Quaternion k1 = new Quaternion (3.0, 7.0, 5.0, 8.0);
      Quaternion k2 = new Quaternion (3.000000000000001, 7.000000000000001,
         5.000000000000001, 8.000000000000001);
      assertEquals(k1, k2, "cannot compare real numbers using == operator");
   }

   @Test
   public void testDivideByRightByZero() {
      Quaternion f1 = new Quaternion (2., 3., 6., 24.);
      Quaternion f2 = new Quaternion (0., 0., 0., 0.);
      assertThrows(RuntimeException.class, () -> f1.divideByRight(f2));
   }

   @Test
   public void testDivideByLeft() {
      Quaternion f1 = new Quaternion (0.25, 0.25, -0.75, 0.5);
      Quaternion f2 = new Quaternion (1., -2., -1., 2.);
      Quaternion qt = f1.divideByLeft (f2);
      assertEquals(new Quaternion (0.15, -0.025, -0.2, -0.175),
              qt, "Wrong quotient from divideByLeft: <" + f1 + "> / <" + f2 + ">");
      assertEquals(new Quaternion (0.25, 0.25, -0.75, 0.5),
              f1, "Do not change the first argument of divideByLeft");
      assertEquals(new Quaternion (1., -2., -1., 2.),
              f2, "Do not change the second argument of divideByLeft");
      qt = f1.divideByLeft (f1);
      assertEquals(new Quaternion (0.25, 0.25, -0.75, 0.5),
              f1, "Do not change the arguments of divideByLeft");
      assertEquals(new Quaternion (1., 0., 0., 0.),
              qt, "Wrong quotient from divideByLeft: <" + f1 + "> / <" + f1 + ">");
      f1 = new Quaternion (1., -1., 1., 1.);
      f2 = new Quaternion (1., 1., -1., 1.);
      qt = f1.divideByLeft (f2);
      assertEquals(new Quaternion (0., 0., 1., 0.),
              qt, "Wrong quotient from divideByLeft: <" + f1 + "> / <" + f2 + ">");
      Quaternion k1 = new Quaternion (3.0, 7.0, 5.0, 8.0);
      Quaternion k2 = new Quaternion (3.000000000000001, 7.000000000000001,
         5.000000000000001, 8.000000000000001);
      assertEquals(k1, k2, "cannot compare real numbers using == operator");
   }

   @Test
   public void testDivideByLeftByZero() {
      Quaternion f1 = new Quaternion (2., 3., 6., 24.);
      Quaternion f2 = new Quaternion (0., 0., 0., 0.);
      assertThrows(RuntimeException.class, () -> f1.divideByLeft(f2));
   }

   boolean consistent1 (Quaternion c) {
      if (!(c.plus(c.opposite()).isZero()))
         return false;
      if (!(c.equals(c.opposite().opposite())))
         return false;
      return c.minus(c).isZero();
   }

   boolean consistent2 (Quaternion c) {
      Quaternion yks = new Quaternion (1., 0., 0., 0.);
      if (!(c.divideByRight(c).equals(yks)))
         return false;
      if (!(c.divideByLeft(c).equals(yks)))
         return false;
      if (!(c.times(c.inverse()).equals(yks)))
         return false;
      if (!(c.inverse().times(c).equals(yks)))
         return false;
      if (!(yks.divideByRight(c.inverse()).equals(c)))
         return false;
      if (!(yks.divideByLeft(c.inverse()).equals(c)))
         return false;
      return c.inverse().inverse().equals(c);
   }

   @Test
   public void testConsistencyPlusMinusOpposite() {
      assertTrue(consistent1 (new Quaternion (1., 1., 1., 1.)), "1+1i+1j+1k is not ok + -");
      assertTrue(consistent1 (new Quaternion (0., 0., 0., 0.)), "0+0i+0j+0k is not ok + -");
      assertTrue(consistent1 (new Quaternion (2., -3., 6., 24.)), "2-3i+6j+24k is not ok + -");
   }

   @Test
   public void testConsistencyTimesInverseDivide() {
      assertTrue(consistent2 (new Quaternion (1., 1., 1., 1.)), "1+1i+1j+1k is not ok * /");
      assertTrue(consistent2 (new Quaternion (2., -3., 6., 24.)), "2-3i+6j+24k is not ok * /");
   }

   @Test
   public void testHashCode() {
      Quaternion q1 = new Quaternion (1., 2., 3., 4.);
      int h1 = q1.hashCode();
      Quaternion q2 = new Quaternion (1., 2., 3., 4.);
      int h2 = q2.hashCode();
      Quaternion q3 = null;
      try {
         q3 = (Quaternion)q1.clone();
      } catch (CloneNotSupportedException ignored) {}
      assert q3 != null;
      int h3 = q3.hashCode();
      assertEquals(h1, h2, "hashCode has to be same for equal objects");
      assertEquals(h1, h3, "hashCode has to be same for clone objects");
      assertEquals(h1, q1.hashCode(), "hashCode has to be same for the same object");
      q2 = new Quaternion (0., 2., 3., 4.);
      h2 = q2.hashCode();
      q3 = new Quaternion (1., 0., 3., 4.);
      h3 = q3.hashCode();
      Quaternion q4 = new Quaternion (1., 2., 0., 4.);
      int h4 = q4.hashCode();
      Quaternion q5 = new Quaternion (1., 2., 3., 0.);
      int h5 = q5.hashCode();
      assertNotEquals(h1, h2, "hashCode does not depend on real part");
      assertNotEquals(h1, h3, "hashCode does not depend on imaginary part i");
      assertNotEquals(h1, h4, "hashCode does not depend on imaginary part j");
      assertNotEquals(h1, h5, "hashCode does not depend on imaginary part k");
   }

   @Test
   public void testHashCodeSymmetry() {
      Quaternion q1 = new Quaternion (1., 2., 3., 4.);
      int h1 = q1.hashCode();
      Quaternion q2 = new Quaternion (1., 2., 4., 3.);
      int h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (1., 3., 2., 4.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (1., 3., 4., 2.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (1., 4., 2., 3.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (1., 4., 3., 2.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (2., 1., 3., 4.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (2., 1., 4., 3.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (2., 3., 1., 4.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (2., 3., 4., 1.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (2., 4., 1., 3.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (2., 4., 3., 1.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (3., 1., 2., 4.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (3., 1., 4., 2.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (3., 2., 1., 4.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (3., 2., 4., 1.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (3., 4., 1., 2.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (3., 4., 2., 1.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (4., 1., 2., 3.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (4., 1., 3., 2.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (4., 2., 1., 3.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (4., 2., 3., 1.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (4., 3., 1., 2.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
      q2 = new Quaternion (4., 3., 2., 1.);
      h2 = q2.hashCode();
      assertNotEquals(h1, h2, "hashCode must not be symmetrical: "
              + q1 + " and " + q2 + " both give " + h1);
   }
}

