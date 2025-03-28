/*
 * Copyright (c) Codice Foundation
 *
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 *
 */
package org.codice.imaging.nitf.core.security.impl;

import java.util.function.Supplier;
import org.codice.imaging.nitf.core.common.FileType;
import org.codice.imaging.nitf.core.security.SecurityMetadata;

/**
 * Builder for SecurityMetadata for NITF 2.1 and NSIF files.
 *
 * This class implements a builder pattern, to produce a SecurityMetadata object
 * (which is immutable).
 *
 * A simple example of its use is:
 * <pre>{@code
 *      SecurityMetadataBuilder21 builder = new SecurityMetadataBuilder21.newInstance(FileType.NITF_TWO_ONE);
 *      builder.securityClassification(SecurityClassification.UNCLASSIFIED);
 *      SecurityMetadata securityMetadata = builder.get();
 * }</pre>
 *
 * The API supports method chaining for a more fluent style, which is useful for
 * more involved metadata requirements:
 * <pre>{@code
 *      SecurityMetadataBuilder21 builder = SecurityMetadataBuilder21.newInstance(FileType.NITF_TWO_ONE);
 *      builder.securityClassification(SecurityClassification.RESTRICTED)
 *              .securityClassificationSystem("AS")
 *              .downgrade("F")
 *              .codewords("AB CD EF")
 *              .releaseInstructions("Only to friends.")
 *              .controlAndHandling("GH")
 *              .downgradeDate("20170812")
 *              .declassificationType("GE")
 *              .declassificationExemption("X1")
 *              .securityControlNumber("Bogus2")
 *              .declassificationDate("20191008")
 *              .classificationText("Classification text3")
 *              .classificationAuthorityType("O")
 *              .securitySourceDate("20081230")
 *              .classificationReason("C")
 *              .classificationAuthority("Some Person");
 *      SecurityMetadata securityMetadata = builder.get();
 * }</pre>
 */
public final class SecurityMetadataBuilder21 extends SecurityMetadataBuilder<SecurityMetadataBuilder21> implements Supplier<SecurityMetadata> {

    private String securityClassificationSystem = "";
    private String classificationReason = "";
    private String nitfDeclassificationType = "";
    private String nitfDeclassificationDate = "";
    private String nitfDeclassificationExemption = "";
    private String nitfDowngrade = "";
    private String nitfDowngradeDate = "";
    private String nitfSecuritySourceDate = "";
    private String nitfClassificationText = "";
    private String nitfClassificationAuthorityType = "";

    /**
     * Constructor.
     *
     * @param fileType the type of NITF file to generate security metadata for
     */
    private SecurityMetadataBuilder21(final FileType fileType) {
        super(fileType);
    }

    /**
     * Constructor.
     *
     * @param fileType the type of NITF file to generate security metadata for (NITF 2.1 or NSIF 1.0).
     *
     * @return new instance of this SecurityMetadataBuilder21
     */
    public static SecurityMetadataBuilder21 newInstance(final FileType fileType) {
        SecurityMetadataBuilder21 builder = new SecurityMetadataBuilder21(fileType);
        builder.instance = builder;
        return builder;
    }

    /**
     * Copy constructor.
     *
     * @param securityMetadata data to initialise from.
     */
    private SecurityMetadataBuilder21(final SecurityMetadata securityMetadata) {
        super(securityMetadata);
    }

    /**
     * Copy constructor method.
     *
     * @param securityMetadata data to initialise from.
     * @return new instance of this SecurityMetadataBuilder21
     */
    public static SecurityMetadataBuilder21 newInstance(final SecurityMetadata securityMetadata) {
        SecurityMetadataBuilder21 builder = new SecurityMetadataBuilder21(securityMetadata);
        builder.instance = builder;
        builder.declassificationExemption(securityMetadata.getDeclassificationExemption());
        builder.declassificationDate(securityMetadata.getDeclassificationDate());
        builder.declassificationType(securityMetadata.getDeclassificationType());
        builder.securityClassificationSystem(securityMetadata.getSecurityClassificationSystem());
        builder.downgrade(securityMetadata.getDowngrade());
        builder.downgradeDate(securityMetadata.getDowngradeDate());
        builder.securitySourceDate(securityMetadata.getSecuritySourceDate());
        builder.classificationAuthorityType(securityMetadata.getClassificationAuthorityType());
        builder.classificationReason(securityMetadata.getClassificationReason());
        builder.classificationText(securityMetadata.getClassificationText());
        return builder;
    }

    /**
     * Set the security classification system.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall contain valid values indicating the national or
     * multinational security system used to classify the file. Country Codes
     * per FIPS PUB 10-4 shall be used to indicate national security systems.
     * The designator "XN" is for classified data generated by a component using
     * NATO security system marking guidance. This code is outside the FIPS 10-4
     * document listing, and was selected to not duplicate that document's
     * existing codes."
     * <p>
     * So system means "which country specified it". This field can be empty
     * indicating no security classification system applied.
     * <p>
     * This field must be set if security-related details (e.g. codewords,
     * control and handling instructions, release instructions, declassification
     * instructions, declassification authorities, declassification dates or
     * declassification exemptions) are set.
     *
     * @param classificationSystem the security classification system (2
     * character country code)
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 securityClassificationSystem(final String classificationSystem) {
        securityClassificationSystem = classificationSystem;
        return this;
    }

    /**
     * Set the classification reason.
     * <p>
     * "This field shall contain values indicating the reason for classifying
     * the file. Valid values are A to G. These correspond to the reasons for
     * original classification per E.O. 12958, Section 1.5.(a) to (g)."
     * <p>
     * An empty string indicates that no file classification reason applies.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     *
     * @param reasonCode the classification reason (1 character), or an empty
     * string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 classificationReason(final String reasonCode) {
        classificationReason = reasonCode;
        return this;
    }

    /**
     * Set the security source date.
     * <p>
     * "This field shall indicate the date of the source used to derive the
     * classification of the file. In the case of multiple sources, the date of
     * the most recent source shall be used."
     * <p>
     * An empty string indicates that a security source date does not apply.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     *
     * @param securitySourceDate the security source date (format CCYYMMDD), or
     * an empty string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 securitySourceDate(final String securitySourceDate) {
        nitfSecuritySourceDate = securitySourceDate;
        return this;
    }

    /**
     * Set the security declassification type.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall contain a valid indicator of the type of security
     * declassification or downgrading instructions which apply to the file.
     * Valid values are DD (=declassify on a specific date), DE (=declassify
     * upon occurrence of an event), GD (=downgrade to a specified level on a
     * specific date), GE (=downgrade to a specified level upon occurrence of an
     * event), O (=OADR), and X (= exempt from automatic declassification)."
     * <p>
     * An empty string indicates that no declassification type applies.
     *
     * @param declassificationType the declassification type (2 characters
     * maximum), or an empty string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 declassificationType(final String declassificationType) {
        nitfDeclassificationType = declassificationType;
        return this;
    }

    /**
     * Set the declassification date.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall indicate the date on which a file is to be declassified
     * if the value in Declassification Type is DD."
     * <p>
     * An empty string means that no declassification date applies.
     *
     * @param declassificationDate the declassification date (format CCYYMMDD),
     * or an empty string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 declassificationDate(final String declassificationDate) {
        nitfDeclassificationDate = declassificationDate;
        return this;
    }

    /**
     * Set the declassification exemption.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall indicate the reason the file is exempt from automatic
     * declassification if the value in Declassification Type is X. Valid values
     * are X1 to X8 and X251 to X259. X1 to X8 correspond to the
     * declassification exemptions found in DOD 5200.1-R, paragraphs 4-202b(1)
     * to (8) for material exempt from the 10-year rule. X251 to X259 correspond
     * to the declassification exemptions found in DOD 5200.1-R, paragraphs
     * 4-301a(1) to (9) for permanently valuable material exempt from the
     * 25-year declassification system."
     * <p>
     * An empty string means that no declassification exemption applies.
     *
     * @param declassificationExemption the declassification exemption (four
     * characters maximum), or an empty string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 declassificationExemption(final String declassificationExemption) {
        nitfDeclassificationExemption = declassificationExemption;
        return this;
    }

    /**
     * Set the security downgrade.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall indicate the classification level to which a file is to
     * be downgraded if the values in Declassification Type are GD or GE. Valid
     * values are S (=Secret), C (=Confidential), R (= Restricted)."
     * <p>
     * An empty string indicates that security downgrading does not apply.
     *
     * @param downgrade the downgrade classification level (1 character), or an
     * empty string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 downgrade(final String downgrade) {
        nitfDowngrade = downgrade;
        return this;
    }

    /**
     * Set the downgrade date.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall indicate the date on which a file is to be downgraded
     * if the value in Declassification Type is GD."
     * <p>
     * An empty string indicates that a security downgrading date does not
     * apply.
     *
     * @param downgradeDate the downgrade date (format CCYYMMDD), or an empty
     * string if downgrading does not apply.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 downgradeDate(final String downgradeDate) {
        nitfDowngradeDate = downgradeDate;
        return this;
    }

    /**
     * Set the classification text.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall be used to provide additional information about file
     * classification to include identification of a declassification or
     * downgrading event if the values in Declassification Type are DE or GE. It
     * may also be used to identify multiple classification sources and/or any
     * other special handling rules. Values are user defined free text."
     * <p>
     * An empty string indicates that additional information about file
     * classification does not apply.
     *
     * @param classificationText the classification text (43 characters
     * maximum), or an empty string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 classificationText(final String classificationText) {
        nitfClassificationText = classificationText;
        return this;
    }

    /**
     * Set the classification authority type.
     * <p>
     * This field is only valid for NITF 2.1 / NSIF 1.0 files.
     * <p>
     * "This field shall indicate the type of authority used to classify the
     * file. Valid values are O (= original classification authority), D (=
     * derivative from a single source), and M (= derivative from multiple
     * sources)."
     * <p>
     * An empty string indicates that classification authority type does not
     * apply.
     *
     * @param classificationAuthorityType classification authority type (1
     * character), or an empty string.
     * @return this builder, to support method chaining
     */
    public SecurityMetadataBuilder21 classificationAuthorityType(final String classificationAuthorityType) {
        nitfClassificationAuthorityType = classificationAuthorityType;
        return this;
    }

    @Override
    void populateVersionSpecific(final SecurityMetadataImpl securityMetadata) {
        securityMetadata.setFileType(nitfFileType);
        securityMetadata.setDowngrade(nitfDowngrade);
        securityMetadata.setSecuritySourceDate(nitfSecuritySourceDate);
        securityMetadata.setDowngradeDate(nitfDowngradeDate);
        securityMetadata.setClassificationReason(classificationReason);
        securityMetadata.setClassificationAuthorityType(nitfClassificationAuthorityType);
        securityMetadata.setClassificationText(nitfClassificationText);
        securityMetadata.setDeclassificationExemption(nitfDeclassificationExemption);
        securityMetadata.setDeclassificationDate(nitfDeclassificationDate);
        securityMetadata.setDeclassificationType(nitfDeclassificationType);
        securityMetadata.setSecurityClassificationSystem(securityClassificationSystem);
    }
}
