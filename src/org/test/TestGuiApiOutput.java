package org.test;

import java.io.IOException;


import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.translation.BingTranslatorGUI;
import org.translation.MicrosoftTranslatorApi;

public class TestGuiApiOutput {

  @Test
  public void testGuiApiOutput() throws IOException, ParseException, InterruptedException {
	  Assert.assertEquals(MicrosoftTranslatorApi.getText(), BingTranslatorGUI.translate());

  }
}