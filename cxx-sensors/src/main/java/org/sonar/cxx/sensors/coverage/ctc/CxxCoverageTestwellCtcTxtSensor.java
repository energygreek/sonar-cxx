/*
 * Sonar C++ Plugin (Community)
 * Copyright (C) 2010-2021 SonarOpenCommunity
 * http://github.com/SonarOpenCommunity/sonar-cxx
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.cxx.sensors.coverage.ctc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.cxx.sensors.coverage.CoverageSensor;

public class CxxCoverageTestwellCtcTxtSensor extends CoverageSensor {

  public static final String REPORT_PATH_KEY = "sonar.cxx.ctctxt.reportPaths";
  public static final String REPORT_ENCODING_DEF = "sonar.cxx.ctctxt.encoding";
  public static final String DEFAULT_ENCODING_DEF = StandardCharsets.UTF_8.name();

  public static List<PropertyDefinition> properties() {
    String category = "CXX External Analyzers";
    String subcategory = "Coverage";
    return Collections.unmodifiableList(Arrays.asList(
      PropertyDefinition.builder(REPORT_PATH_KEY)
        .name("Testwell CTC++ TXT coverage report(s)")
        .description(
          "List of paths to reports containing coverage data, relative to projects root."
            + " The values are separated by commas."
            + " See <a href='https://github.com/SonarOpenCommunity/sonar-cxx/wiki/Get-code-coverage-metrics'>"
            + "here</a> for supported formats.")
        .category(category)
        .subCategory(subcategory)
        .onQualifiers(Qualifiers.PROJECT)
        .multiValues(true)
        .build(),
      PropertyDefinition.builder(REPORT_ENCODING_DEF)
        .defaultValue(DEFAULT_ENCODING_DEF)
        .name("Testwell CTC++ TXT Report Encoding")
        .description("The encoding to use when reading the coverage report. Leave empty to use parser's default UTF-8.")
        .category(category)
        .subCategory(subcategory)
        .onQualifiers(Qualifiers.PROJECT)
        .build()
    ));
  }

  public CxxCoverageTestwellCtcTxtSensor() {
    super(REPORT_PATH_KEY, new TestwellCtcTxtParser());
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
      .name("CXX Testwell CTC++ TXT coverage report import")
      .onlyOnLanguages("cxx", "cpp", "c")
      .onlyWhenConfiguration(conf -> conf.hasKey(REPORT_PATH_KEY));
  }

}
