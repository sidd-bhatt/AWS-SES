package com.app.emailservice.utility;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.app.emailservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AWSEmailService {
    @Autowired
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${atlantis.aws.email.source}")
    private String source;
    static final String SUBJECT = "Benchmark Greetings";
    static final String HTMLBODY = "<div style=\"padding: 3%; border-style: solid;\"><p style=\"text-align: center;\"><img src=\"https://benchmarkuniverse.com/digital-solutions/imgs/logo.png\" alt=\"Benchmark Education Company Logo\" width=\"260\" height=\"70\" style=\"text-align: center;\"></p><br><p>Subject: Start Your 30-Day Free Trial for Benchmark Education!</p><p>Dear ${CUSTOMER_NAME},</p><p>We're excited to give you instant access to review products from Benchmark Education. Your access will expire on blank. The products included in this trail are listed below.</p><p><ul>${PRODUCT_LIST}</ul></p><p>To get started, simply click on the URL provided in this email. This will direct you to a landing page where you will be prompted to create your password and accept our terms and conditions.</p><p style=\"color: rgb(60, 60, 213)\">You can find helpful videos on using our eLearning Platform, Benchmark Universe, within Benchmark Academy. You can access these videos by clicking on the Training for Curriculum Resources within the Benchmark Academy section on your dashboard (or by using the dropdown in your library view).</p><p style=\"color: rgb(60, 60, 213)\">You will only have access to a teacher account (no student accounts), so you will not have the ability to assign work to students. If you'd like to see the assignment and reporting functionality, please inquire with your sales representative. </p><p>Url: ${URL}<br> Username: ${USERNAME} </p><p><b>Please note that the URL will only be valid for 48 hrs.</b></p><p>Should you have any questions, or concerns, or require further assistance, please do not hesitate to reach out to us.</p><p>Best regards, <br>Benchmark Education</p> <br><footer style=\"color: rgb(146, 143, 143);\">Copyright &copy; 2021 Benchmark Universe<br>145 Huguenot St, New Rochelle, NY 10805<br>Toll-Free: (877) 236-2465<br>Fax: (877) 732-8273<br>E-Mail: <a href=\"\">info@benchmarkeducation.com</a><br>Website: <a href=\"https://www.benchmarkeducation.com/\">benchmarkeducation.com</a></footer></div>";
    static final String TEXTBODY = "This email was sent through Amazon SES " + "using the AWS SDK for Java.";

    public void sendEmail(User user) throws AmazonSimpleEmailServiceException {
        try {

            //product list from database
            List<String> productList = new ArrayList<>();
            productList.add("Product 1");
            productList.add("Product 2");
            StringBuilder list=new StringBuilder();
            for(String product:productList) {
                list.append("<li>").append(product).append("</li>");
            }

            //setting the dynamic data for HTMLBODY
            String htmlBody=HTMLBODY
                    .replace("${CUSTOMER_NAME}", user.getName())
                    .replace("${USERNAME}", user.getEmail())
                    .replace("${URL}", "NBQKFYM9XB")
                    .replace("${PRODUCT_LIST}", list);

            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(region).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(user.getEmail()))
                    .withMessage(new Message().withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody))
                                    .withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
                            .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(source);
            client.sendEmail(request);
            System.out.println("Email sent!");

        } catch (AmazonSimpleEmailServiceException ex) {
            throw new AmazonSimpleEmailServiceException("Email not sent!. "+ex.getErrorCode());
        }
    }
}

