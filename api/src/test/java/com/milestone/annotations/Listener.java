package com.milestone.annotations;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import com.milestone.annotations.KnownIssue;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Listener extends RunListener {
  private static final List<String> knownIssues = new ArrayList<>();
  private static final String REPORT_PATH = "target/known-issues-report.txt";

  @Override
  public void testFailure(Failure failure) throws Exception {
    Description description = failure.getDescription();
    KnownIssue knownIssue = description.getAnnotation(KnownIssue.class);

    if (knownIssue != null) {
      String issue = String.format("[%s] Test: %s - JIRA: %s - Description: %s",
          LocalDateTime.now(),
          description.getDisplayName(),
          knownIssue.jiraId(),
          knownIssue.description());

      knownIssues.add(issue);

      if (!knownIssue.shouldFail()) {
        // If shouldFail is false, we'll mark this test as skipped instead of failed
        throw new org.junit.AssumptionViolatedException(
            String.format("Known issue: %s - This test is marked as a known issue and will not fail the build.",
                knownIssue.jiraId()));
      }
    }
  }

  @Override
  public void testRunFinished(org.junit.runner.Result result) throws Exception {
    generateKnownIssuesReport();
  }

  private void generateKnownIssuesReport() {
    try (FileWriter writer = new FileWriter(REPORT_PATH, true)) {
      if (!knownIssues.isEmpty()) {
        writer.write("\n=== Known Issues Report ===\n");
        writer.write(String.format("Generated at: %s\n\n", LocalDateTime.now()));

        for (String issue : knownIssues) {
          writer.write(issue + "\n");
        }

        writer.write("\nTotal known issues: " + knownIssues.size() + "\n");
        writer.write("==========================\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}