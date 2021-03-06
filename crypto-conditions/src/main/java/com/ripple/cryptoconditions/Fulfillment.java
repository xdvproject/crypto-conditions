package com.ripple.cryptoconditions;

/*-
 * ========================LICENSE_START=================================
 * Crypto Conditions
 * %%
 * Copyright (C) 2016 - 2018 Ripple Labs
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

/**
 * An implementation of a crypto-conditions Fulfillment.
 *
 * @see "https://datatracker.ietf.org/doc/draft-thomas-crypto-conditions/"
 */
public interface Fulfillment<C extends Condition> {

  /**
   * Accessor for the type of this fulfillment.
   *
   * @return A {@link CryptoConditionType} for this fulfillment.
   */
  CryptoConditionType getType();

  /**
   * Accessor for the condition that corresponds to this fulfillment.
   *
   * @return A {@link Condition} that can be fulfilled by this fulfillment.
   */
  C getDerivedCondition();

  /**
   * <p>Validation of a fulfillment (F) against a condition (C) and a message (M), in the majority
   * of cases, follows the steps below.</p>
   *
   * <p>1.  The implementation must derive a condition from the fulfillment and ensure that the
   * derived condition (D) matches the given condition (C).</p>
   *
   * <p>2.  If the fulfillment is a simple crypto-condition AND is based upon a signature scheme
   * (such as RSA-PSS or ED25519) then any signatures in the fulfillment (F) must be verified, using the appropriate
   * signature verification algorithm, against the corresponding public key, also provided in the fulfillment and the
   * message (M) (which may be empty).</p>
   *
   * <p>3. If the fulfillment is a compound crypto-condition then the sub-fulfillments MUST each be
   * validated.  In the case of the PREFIX-SHA-256 type, the sub-fulfillment MUST be valid for (F) to be valid. In the
   * case of the THRESHOLD-SHA-256 type, the number of valid sub-fulfillments must be equal or greater than the
   * threshold defined in (F).</p>
   *
   * <p>If the derived condition (D) matches the input condition (C) AND the boolean circuit defined
   * by the fulfillment evaluates to TRUE then the fulfillment (F) fulfills the condition (C).</p>
   *
   * <p>A more detailed validation algorithm for each crypto-condition type is provided in the
   * Javadoc of each {@link #verify(Condition, byte[])} method. In each case the notation F.x or C.y implies the decoded
   * value of the field named x from the fulfillment and the decoded value of the field named y from the Condition,
   * respectively.</p>
   *
   * @param condition A {@link Condition} that this fulfillment should verify.
   * @param message   An optionally-empty byte array that, if present, is part of validating the supplied condition.
   *
   * @return {@code true} if this fulfillment validates the supplied condition and message; {@code false} otherwise.
   *
   * @see "https://tools.ietf.org/html/draft-thomas-crypto-conditions-04#section-5"
   */
  boolean verify(Condition condition, byte[] message);

  /**
   * <p>Validation of a fulfillment (F) against a condition (C) and an empty message (M). See
   * Javadoc for {@link #verify(Condition, byte[])} for more details about the specific details of this method.</p>
   *
   * @param condition A {@link Condition} that this fulfillment should verify.
   *
   * @return <tt>true</tt> if the supplied conndition verifies; <tt>false</tt> otherwise.
   *
   * @see "https://tools.ietf.org/html/draft-thomas-crypto-conditions-04#section-5"
   */
  default boolean verify(Condition condition) {
    return verify(condition, "".getBytes());
  }
}
