package com.javacowboy.ideal.service;

import java.util.Date;
import java.util.List;

import com.javacowboy.ideal.Constants;
import com.javacowboy.ideal.mail.SendMail;
import com.javacowboy.ideal.model.dto.ksl.KslResultDto;
import com.javacowboy.ideal.view.Decorator;

public class MailService extends Service {
	
	public static final String RESULTS_MAIL_SUBJECT = "Eye-Deal deals";
	
	ResultsService resultsService = ServiceFactory.getResultsService();

	public void sendResultsEmail(List<KslResultDto> results, String ... recipients) {
		if(results != null && !results.isEmpty()) {
			if(Constants.hasEmailItemRestriction()) {
				resultsService.removeViewedItems(results, Constants.emailItemMaxViews);
			}
			if(!results.isEmpty()) {
				String html = buildResultsHtml(results);
				SendMail.sendHtmlMessage(buildResultsSubject(), html, recipients);
			}
		}
	}

	private String buildResultsSubject() {
		return RESULTS_MAIL_SUBJECT + "  " + Decorator.shortDateTime(new Date()); 
	}
	
	public String buildResultsHtml(List<KslResultDto> results) {
		String paramSummary = "";
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body><table border='1'>");
		if(results == null || results.isEmpty()) {
			buildSummary(builder, "No Results Found.");
		}else {
			for(KslResultDto result : results) {
				if(!paramSummary.equals(Decorator.summary(result.getParameterDto()))) {
					builder.append("</table>");
					paramSummary = Decorator.summary(result.getParameterDto());
					buildSummary(builder, paramSummary);
					builder.append("<table border='1'>");
				}
				buildResultDetail(builder, result);
			}
		}
		builder.append("</table></body></html>");
		return builder.toString();
	}
	
	private void buildResultDetail(StringBuilder builder, KslResultDto result) {
		builder.append("<tr>")
			.append("<td>");
				buildAnchor(builder, result.getAdHref(), "<img src='" + result.getImageHref() + "'/>");
			builder.append("</td>")
			.append("<td>");
				String price = Decorator.money(result.getPrice());
				if(result.getOriginalPrice() != null) {
					price += " (WAS: " + Decorator.money(result.getOriginalPrice()) + ")";
				}
				price += " " + result.getTitle();
				buildAnchor(builder, result.getAdHref(), price);
			builder.append("</td>")
			.append("<td>")
				.append(result.getTime())
			.append("</td>")
			.append("<td>")
				.append(result.getLocation())
			.append("</td>")
			.append("<td>")
				.append(result.getDescription())
			.append("</td>")
		.append("</tr>");
	}

	/*//Here is a div layout
	public String buildResultsHtml(List<KslResultDto> results) {
		//TODO matthew look at using freemarker
		String paramSummary = "";
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>");
		for(KslResultDto result : results) {
			if(!paramSummary.equals(Decorator.summary(result.getParameterDto()))) {
				paramSummary = Decorator.summary(result.getParameterDto());
				buildSummary(builder, paramSummary);
			}
			buildResultDetail(builder, result);
		}
		builder.append("</body></html>");
		return builder.toString();
	}

	private void buildResultDetail(StringBuilder builder, KslResultDto result) {
		builder.append("<div class='result'>")
			.append("<div class='resultImage'>");
				buildAnchor(builder, result.getAdHref(), "<img src='" + result.getImageHref() + "'/>");
			builder.append("</div>")
			.append("<div class='resultDetail'>");
				buildAnchor(builder, result.getAdHref(), Decorator.money(result.getPrice()) + " " + result.getTitle());
			builder.append("</div>")
		.append("</div>");
	}*/
	
	private void buildSummary(StringBuilder builder, String paramSummary) {
		builder.append("<h2>")
			.append(paramSummary)
		.append("</h2>");
	}
	
	private void buildAnchor(StringBuilder builder, String href, String innerPart) {
		builder.append("<a href='").append(href).append("' target='_blank'>")
		.append(innerPart).append("</a>");
	}
}
