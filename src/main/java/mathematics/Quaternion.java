package mathematics;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Quaternions. Basic operations. */
public class Quaternion {
   private final double a;
   private final double b;
   private final double c;
   private final double d;

   private static final double DELTA = 0.000000001;

   /** Constructor from four double values.
    * @param a real part
    * @param b imaginary part i
    * @param c imaginary part j
    * @param d imaginary part k
    */
   public Quaternion (double a, double b, double c, double d) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
   }

   /** Real part of the quaternion.
    * @return real part
    */
   public double getRpart() {
      return a;
   }

   /** Imaginary part i of the quaternion. 
    * @return imaginary part i
    */
   public double getIpart() {
      return b;
   }

   /** Imaginary part j of the quaternion. 
    * @return imaginary part j
    */
   public double getJpart() {
      return c;
   }

   /** Imaginary part k of the quaternion. 
    * @return imaginary part k
    */
   public double getKpart() {
      return d;
   }

   /** Conversion from the string to the quaternion.
    * Reverse to <code>toString</code> method.
    * @throws IllegalArgumentException if string s does not represent
    *     a quaternion (defined by the <code>toString</code> method)
    * @param s string of form produced by the <code>toString</code> method
    * @return a quaternion represented by string s
    */
   public static Quaternion valueOf (String s) {
      String[] parts = new String[4];
      StringBuilder currentElem = new StringBuilder();
      for (Character c : s.toCharArray()) {
         if (c.equals('+')) {
            parts[getArrayIndex(currentElem.charAt(currentElem.length() - 1))] = currentElem.toString();
            currentElem = new StringBuilder();
         } else if (c.equals('-')) {
            parts[getArrayIndex((currentElem.isEmpty()) ? 0 : currentElem.charAt(currentElem.length() - 1))] = currentElem.toString();
            currentElem = new StringBuilder();
            currentElem.append(c);
         } else {
            currentElem.append(c);
         }
      }
      parts[getArrayIndex(currentElem.charAt(currentElem.length() - 1))] = currentElem.toString();
      return new Quaternion(Double.parseDouble(parts[0]),
              Double.parseDouble(parts[1].substring(0, parts[1].length() - 1)),
              Double.parseDouble(parts[2].substring(0, parts[2].length() - 1)),
              Double.parseDouble(parts[3].substring(0, parts[3].length() - 1)));
   }

   private static int getArrayIndex(char element) {
      return switch (element) {
         case 'i' -> 1;
         case 'j' -> 2;
         case 'k' -> 3;
         default -> 0;
      };
   }

   /** Clone of the quaternion.
    * @return independent clone of <code>this</code>
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      return new Quaternion(getRpart(), getIpart(), getJpart(), getKpart());
   }

   /** Test whether the quaternion is zero. 
    * @return true, if the real part and all the imaginary parts are (close to) zero
    */
   public boolean isZero() {
      return DELTA > Math.abs(getRpart())
              && DELTA > Math.abs(getIpart())
              && DELTA > Math.abs(getJpart())
              && DELTA > Math.abs(getKpart());
   }

   /** Conjugate of the quaternion. Expressed by the formula 
    *     conjugate(a+bi+cj+dk) = a-bi-cj-dk
    * @return conjugate of <code>this</code>
    */
   public Quaternion conjugate() {
      return new Quaternion(getRpart(), 0 - getIpart(), 0 - getJpart(), 0 - getKpart());
   }

   /** Opposite of the quaternion. Expressed by the formula 
    *    opposite(a+bi+cj+dk) = -a-bi-cj-dk
    * @return quaternion <code>-this</code>
    */
   public Quaternion opposite() {
      return new Quaternion(0, 0, 0, 0).minus(this);
   }

   /** Sum of quaternions. Expressed by the formula 
    *    (a1+b1i+c1j+d1k) + (a2+b2i+c2j+d2k) = (a1+a2) + (b1+b2)i + (c1+c2)j + (d1+d2)k
    * @param q addend
    * @return quaternion <code>this+q</code>
    */
   public Quaternion plus (Quaternion q) {
      return new Quaternion(getRpart() + q.getRpart(),
              getIpart() + q.getIpart(),
              getJpart() + q.getJpart(),
              getKpart() + q.getKpart());
   }

   /** Difference of quaternions. Expressed as addition to the opposite.
    * @param q subtrahend
    * @return quaternion <code>this-q</code>
    */
   public Quaternion minus (Quaternion q) {
      return new Quaternion(getRpart() - q.getRpart(),
              getIpart() - q.getIpart(),
              getJpart() - q.getJpart(),
              getKpart() - q.getKpart());
   }

   /** Product of quaternions. Expressed by the formula
    *  (a1+b1i+c1j+d1k) * (a2+b2i+c2j+d2k) = (a1a2-b1b2-c1c2-d1d2) + (a1b2+b1a2+c1d2-d1c2)i +
    *  (a1c2-b1d2+c1a2+d1b2)j + (a1d2+b1c2-c1b2+d1a2)k
    * @param q factor
    * @return quaternion <code>this*q</code>
    */
   public Quaternion times (Quaternion q) {
      double rPart = getRpart() * q.getRpart() -
              getIpart() * q.getIpart() -
              getJpart() * q.getJpart() -
              getKpart() * q.getKpart();
      double iPart = getRpart() * q.getIpart() +
              getIpart() * q.getRpart() +
              getJpart() * q.getKpart() -
              getKpart() * q.getJpart();
      double jPart = getRpart() * q.getJpart() -
              getIpart() * q.getKpart() +
              getJpart() * q.getRpart() +
              getKpart() * q.getIpart();
      double kPart = getRpart() * q.getKpart() +
              getIpart() * q.getJpart() -
              getJpart() * q.getIpart() +
              getKpart() * q.getRpart();
      return new Quaternion(rPart, iPart, jPart, kPart);
   }

   /** Multiplication by a coefficient.
    * @param r coefficient
    * @return quaternion <code>this*r</code>
    */
   public Quaternion times (double r) {
      return new Quaternion(getRpart() * r, getIpart() * r, getJpart() * r, getKpart() * r);
   }

   /** Inverse of the quaternion. Expressed by the formula
    *     1/(a+bi+cj+dk) = a/(a*a+b*b+c*c+d*d) + 
    *     ((-b)/(a*a+b*b+c*c+d*d))i + ((-c)/(a*a+b*b+c*c+d*d))j + ((-d)/(a*a+b*b+c*c+d*d))k
    * @return quaternion <code>1/this</code>
    */
   public Quaternion inverse() {
      if (isZero()) {
         throw new RuntimeException();
      }
      return conjugate().times(1 / normSquared());
   }

   /** Norm of the quaternion. Expressed by the formula
    *     norm(a+bi+cj+dk) = Math.sqrt(a*a+b*b+c*c+d*d)
    * @return norm of <code>this</code> (norm is a real number)
    */
   public double norm() {
      return Math.sqrt(normSquared());
   }

   private double normSquared() {
      return Math.pow(getRpart(), 2) +
              Math.pow(getIpart(), 2) +
              Math.pow(getJpart(), 2) +
              Math.pow(getKpart(), 2);
   }

   /** Right quotient of quaternions. Expressed as multiplication to the inverse.
    * @param q (right) divisor
    * @return quaternion <code>this*inverse(q)</code>
    */
   public Quaternion divideByRight (Quaternion q) {
      if (q.isZero()) {
         throw new RuntimeException("Nullkvaterniooniga jagada ei saa!");
      }
      return this.times(q.inverse());
   }

   /** Left quotient of quaternions.
    * @param q (left) divisor
    * @return quaternion <code>inverse(q)*this</code>
    */
   public Quaternion divideByLeft (Quaternion q) {
      if (q.isZero()) {
         throw new RuntimeException("Nullkvaterniooniga jagada ei saa!");
      }
      return q.inverse().times(this);
   }

   /** Dot product of quaternions. (p*conjugate(q) + q*conjugate(p))/2
    * @param q factor
    * @return dot product of this and q
    */
   public Quaternion dotMult (Quaternion q) {
      return this.times(q.conjugate()).plus(q.times(conjugate())).times(0.5);
   }

   /** Equality test of quaternions. Difference of equal numbers
    *     is (close to) zero.
    * @param qo second quaternion
    * @return logical value of the expression <code>this.equals(qo)</code>
    */
   @Override
   public boolean equals (Object qo) {
      if (!(qo instanceof Quaternion other)) {
         return false;
      }
      return DELTA > Math.abs(getRpart() - other.getRpart())
              && DELTA > Math.abs(getIpart() - other.getIpart())
              && DELTA > Math.abs(getJpart() - other.getJpart())
              && DELTA > Math.abs(getKpart() - other.getKpart());
   }

   /** Integer hashCode has to be the same for equal objects.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      return Objects.hash(getRpart(), getIpart(), getJpart(), getKpart());
   }

   /** Conversion of the quaternion to the string.
    * @return a string form of this quaternion:
    * "a+bi+cj+dk"
    * (without any brackets)
    */
   @Override
   public String toString() {
      String opAfterR = getIpart() >= 0 ? "+" : "-";
      String opAfterI = getJpart() >= 0 ? "i+" : "i-";
      String opAfterJ = getKpart() >= 0 ? "j+" : "j-";
      double iStr = getIpart() >= 0 ? getIpart() : 0 - getIpart();
      double jStr = getJpart() >= 0 ? getJpart() : 0 - getJpart();
      double kStr = getKpart() >= 0 ? getKpart() : 0 - getKpart();
      return getRpart() + opAfterR +
              iStr + opAfterI +
              jStr + opAfterJ +
              kStr + "k";
   }

   /** Main method for testing purposes. 
    * @param args command line parameters
    */
   public static void main (String[] args) {
      System.out.println(Quaternion.valueOf("1+2i-3j-4k"));
   }

}

