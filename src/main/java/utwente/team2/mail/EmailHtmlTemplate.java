package utwente.team2.mail;

import utwente.team2.dao.UserDao;
import utwente.team2.model.User;
import utwente.team2.settings.ApplicationSettings;

public class EmailHtmlTemplate {
    

    public static String createEmailHtml(String username, String token, String messageBody, String messageButton, String url) {
        User user = UserDao.instance.getUserDetails(username);
        return part1 + user.getFirstName() + " " + user.getLastName() + part2 + messageBody + part3 + url + token + part4 + messageButton + part5;
    }
    
    
    private static String part1 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
            "" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">" +
            "<head>" +
            "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->" +
            "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>" +
            "<meta content=\"width=device-width\" name=\"viewport\"/>" +
            "<!--[if !mso]><!-->" +
            "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>" +
            "<!--<![endif]-->" +
            "<title></title>" +
            "<!--[if !mso]><!-->" +
            "<!--<![endif]-->" +
            "<style type=\"text/css\">" +
            "body {" +
            "margin: 0;" +
            "padding: 0;" +
            "}" +
            "" +
            "table," +
            "td," +
            "tr {" +
            "vertical-align: top;" +
            "border-collapse: collapse;" +
            "}" +
            "" +
            "* {" +
            "line-height: inherit;" +
            "}" +
            "" +
            "a[x-apple-data-detectors=true] {" +
            "color: inherit !important;" +
            "text-decoration: none !important;" +
            "}" +
            "" +
            ".ie-browser table {" +
            "table-layout: fixed;" +
            "}" +
            "" +
            "[owa] .img-container div," +
            "[owa] .img-container button {" +
            "display: block !important;" +
            "}" +
            "" +
            "[owa] .fullwidth button {" +
            "width: 100% !important;" +
            "}" +
            "" +
            "[owa] .block-grid .col {" +
            "display: table-cell;" +
            "float: none !important;" +
            "vertical-align: top;" +
            "}" +
            "" +
            ".ie-browser .block-grid," +
            ".ie-browser .num12," +
            "[owa] .num12," +
            "[owa] .block-grid {" +
            "width: 600px !important;" +
            "}" +
            "" +
            ".ie-browser .mixed-two-up .num4," +
            "[owa] .mixed-two-up .num4 {" +
            "width: 200px !important;" +
            "}" +
            "" +
            ".ie-browser .mixed-two-up .num8," +
            "[owa] .mixed-two-up .num8 {" +
            "width: 400px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.two-up .col," +
            "[owa] .block-grid.two-up .col {" +
            "width: 300px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.three-up .col," +
            "[owa] .block-grid.three-up .col {" +
            "width: 300px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.four-up .col [owa] .block-grid.four-up .col {" +
            "width: 150px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.five-up .col [owa] .block-grid.five-up .col {" +
            "width: 120px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.six-up .col," +
            "[owa] .block-grid.six-up .col {" +
            "width: 100px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.seven-up .col," +
            "[owa] .block-grid.seven-up .col {" +
            "width: 85px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.eight-up .col," +
            "[owa] .block-grid.eight-up .col {" +
            "width: 75px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.nine-up .col," +
            "[owa] .block-grid.nine-up .col {" +
            "width: 66px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.ten-up .col," +
            "[owa] .block-grid.ten-up .col {" +
            "width: 60px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.eleven-up .col," +
            "[owa] .block-grid.eleven-up .col {" +
            "width: 54px !important;" +
            "}" +
            "" +
            ".ie-browser .block-grid.twelve-up .col," +
            "[owa] .block-grid.twelve-up .col {" +
            "width: 50px !important;" +
            "}" +
            "</style>" +
            "<style id=\"media-query\" type=\"text/css\">" +
            "@media only screen and (min-width: 620px) {" +
            ".block-grid {" +
            "width: 600px !important;" +
            "}" +
            "" +
            ".block-grid .col {" +
            "vertical-align: top;" +
            "}" +
            "" +
            ".block-grid .col.num12 {" +
            "width: 600px !important;" +
            "}" +
            "" +
            ".block-grid.mixed-two-up .col.num3 {" +
            "width: 150px !important;" +
            "}" +
            "" +
            ".block-grid.mixed-two-up .col.num4 {" +
            "width: 200px !important;" +
            "}" +
            "" +
            ".block-grid.mixed-two-up .col.num8 {" +
            "width: 400px !important;" +
            "}" +
            "" +
            ".block-grid.mixed-two-up .col.num9 {" +
            "width: 450px !important;" +
            "}" +
            "" +
            ".block-grid.two-up .col {" +
            "width: 300px !important;" +
            "}" +
            "" +
            ".block-grid.three-up .col {" +
            "width: 200px !important;" +
            "}" +
            "" +
            ".block-grid.four-up .col {" +
            "width: 150px !important;" +
            "}" +
            "" +
            ".block-grid.five-up .col {" +
            "width: 120px !important;" +
            "}" +
            "" +
            ".block-grid.six-up .col {" +
            "width: 100px !important;" +
            "}" +
            "" +
            ".block-grid.seven-up .col {" +
            "width: 85px !important;" +
            "}" +
            "" +
            ".block-grid.eight-up .col {" +
            "width: 75px !important;" +
            "}" +
            "" +
            ".block-grid.nine-up .col {" +
            "width: 66px !important;" +
            "}" +
            "" +
            ".block-grid.ten-up .col {" +
            "width: 60px !important;" +
            "}" +
            "" +
            ".block-grid.eleven-up .col {" +
            "width: 54px !important;" +
            "}" +
            "" +
            ".block-grid.twelve-up .col {" +
            "width: 50px !important;" +
            "}" +
            "}" +
            "" +
            "@media (max-width: 620px) {" +
            "" +
            ".block-grid," +
            ".col {" +
            "min-width: 320px !important;" +
            "max-width: 100% !important;" +
            "display: block !important;" +
            "}" +
            "" +
            ".block-grid {" +
            "width: 100% !important;" +
            "}" +
            "" +
            ".col {" +
            "width: 100% !important;" +
            "}" +
            "" +
            ".col>div {" +
            "margin: 0 auto;" +
            "}" +
            "" +
            "img.fullwidth," +
            "img.fullwidthOnMobile {" +
            "max-width: 100% !important;" +
            "}" +
            "" +
            ".no-stack .col {" +
            "min-width: 0 !important;" +
            "display: table-cell !important;" +
            "}" +
            "" +
            ".no-stack.two-up .col {" +
            "width: 50% !important;" +
            "}" +
            "" +
            ".no-stack .col.num4 {" +
            "width: 33% !important;" +
            "}" +
            "" +
            ".no-stack .col.num8 {" +
            "width: 66% !important;" +
            "}" +
            "" +
            ".no-stack .col.num4 {" +
            "width: 33% !important;" +
            "}" +
            "" +
            ".no-stack .col.num3 {" +
            "width: 25% !important;" +
            "}" +
            "" +
            ".no-stack .col.num6 {" +
            "width: 50% !important;" +
            "}" +
            "" +
            ".no-stack .col.num9 {" +
            "width: 75% !important;" +
            "}" +
            "" +
            ".video-block {" +
            "max-width: none !important;" +
            "}" +
            "" +
            ".mobile_hide {" +
            "min-height: 0px;" +
            "max-height: 0px;" +
            "max-width: 0px;" +
            "display: none;" +
            "overflow: hidden;" +
            "font-size: 0px;" +
            "}" +
            "" +
            ".desktop_hide {" +
            "display: block !important;" +
            "max-height: none !important;" +
            "}" +
            "}" +
            "</style>" +
            "</head>" +
            "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #283C4B;\">" +
            "<style id=\"media-query-bodytag\" type=\"text/css\">" +
            "@media (max-width: 620px) {" +
            "  .block-grid {" +
            "    min-width: 320px!important;" +
            "    max-width: 100%!important;" +
            "    width: 100%!important;" +
            "    display: block!important;" +
            "  }" +
            "  .col {" +
            "    min-width: 320px!important;" +
            "    max-width: 100%!important;" +
            "    width: 100%!important;" +
            "    display: block!important;" +
            "  }" +
            "  .col > div {" +
            "    margin: 0 auto;" +
            "  }" +
            "  img.fullwidth {" +
            "    max-width: 100%!important;" +
            "    height: auto!important;" +
            "  }" +
            "  img.fullwidthOnMobile {" +
            "    max-width: 100%!important;" +
            "    height: auto!important;" +
            "  }" +
            "  .no-stack .col {" +
            "    min-width: 0!important;" +
            "    display: table-cell!important;" +
            "  }" +
            "  .no-stack.two-up .col {" +
            "    width: 50%!important;" +
            "  }" +
            "  .no-stack.mixed-two-up .col.num4 {" +
            "    width: 33%!important;" +
            "  }" +
            "  .no-stack.mixed-two-up .col.num8 {" +
            "    width: 66%!important;" +
            "  }" +
            "  .no-stack.three-up .col.num4 {" +
            "    width: 33%!important" +
            "  }" +
            "  .no-stack.four-up .col.num3 {" +
            "    width: 25%!important" +
            "  }" +
            "}" +
            "</style>" +
            "<!--[if IE]><div class=\"ie-browser\"><![endif]-->" +
            "<table bgcolor=\"#283C4B\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #283C4B; width: 100%;\" valign=\"top\" width=\"100%\">" +
            "<tbody>" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">" +
            "<td style=\"word-break: break-word; vertical-align: top; border-collapse: collapse;\" valign=\"top\">" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#283C4B\"><![endif]-->" +
            "<div style=\"background-color:transparent;\">" +
            "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;;\">" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->" +
            "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top;;\">" +
            "<div style=\"width:100% !important;\">" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">" +
            "<!--<![endif]-->" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">" +
            "<tbody>" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">" +
            "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px; border-collapse: collapse;\" valign=\"top\">" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"0\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 0px solid transparent; height: 0px;\" valign=\"top\" width=\"100%\">" +
            "<tbody>" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">" +
            "<td height=\"0\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; border-collapse: collapse;\" valign=\"top\"><span></span></td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
            "</td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "</div>" +
            "<!--<![endif]-->" +
            "</div>" +
            "</div>" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div style=\"background-color:#283C4B;\">" +
            "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #296785;;\">" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#296785;\">" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#283C4B;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#296785\"><![endif]-->" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#296785;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->" +
            "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top;;\">" +
            "<div style=\"width:100% !important;\">" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">" +
            "<!--<![endif]-->" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 20px; padding-left: 20px; padding-top: 30px; padding-bottom: 20px; font-family: Arial, sans-serif\"><![endif]-->" +
            "<div style=\"color:#FFFFFF;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:30px;padding-right:20px;padding-bottom:20px;padding-left:20px;\">" +
            "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 14px; color: #FFFFFF;\">" +
            "<p style=\"font-size: 18px; line-height: 21px; text-align: center; margin: 0;\"><strong><span style=\"font-size: 24px; line-height: 28px;\">RUNNER</span></strong></p>" +
            "</div>" +
            "</div>" +
            "<!--[if mso]></td></tr></table><![endif]-->" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "</div>" +
            "<!--<![endif]-->" +
            "</div>" +
            "</div>" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div style=\"background-color:#283C4B;\">" +
            "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;;\">" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#283C4B;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#FFFFFF;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:15px;\"><![endif]-->" +
            "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top;;\">" +
            "<div style=\"width:100% !important;\">" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:15px; padding-right: 0px; padding-left: 0px;\">" +
            "<!--<![endif]-->" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->" +
            "<div style=\"color:#283C4B;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:150%;padding-top:10px;padding-right:30px;padding-bottom:10px;padding-left:30px;\">" +
            "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 18px; color: #283C4B;\">" +
            "<p style=\"font-size: 12px; line-height: 24px; text-align: center; margin: 0;\"><span style=\"font-size: 16px;\"><strong><span style=\"line-height: 24px; font-size: 16px;\">Dear ";



    private static String part2 = "</span></strong></span></p>" +
            "</div>" +
            "</div>" +
            "<!--[if mso]></td></tr></table><![endif]-->" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 10px; padding-bottom: 0px; font-family: Arial, sans-serif\"><![endif]-->" +
            "<div style=\"color:#283C4B;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:150%;padding-top:10px;padding-right:30px;padding-bottom:0px;padding-left:30px;\">" +
            "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 18px; color: #283C4B;\">" +
            "<p style=\"font-size: 12px; line-height: 24px; text-align: center; margin: 0;\"><span style=\"font-size: 16px;\">";


    private static String part3 = "</span></p>" +
            "</div>" +
            "</div>" +
            "<!--[if mso]></td></tr></table><![endif]-->" +
            "<div align=\"center\" class=\"button-container\" style=\"padding-top:25px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">" +

            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"><tr><td style=\"padding-top: 25px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"" + ApplicationSettings.DOMAIN + "/\" style=\"height:39pt; width:199.5pt; v-text-anchor:middle;\" arcsize=\"8%\" stroke=\"false\" fillcolor=\"#296785\"><w:anchorlock/><v:textbox inset=\"0,0,0,0\"><center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:16px\"><![endif]--><a href=\"";


    private static String part4 = "\" style=\"-webkit-text-size-adjust: none; text-decoration: none; display: inline-block; color: #ffffff; background-color: #296785; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width: auto; width: auto; border-top: 1px solid #296785; border-right: 1px solid #296785; border-bottom: 1px solid #296785; border-left: 1px solid #296785; padding-top: 10px; padding-bottom: 10px; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; text-align: center; mso-border-alt: none; word-break: keep-all;\" target=\"_blank\"><span style=\"padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;\">" +
            "<span style=\"font-size: 16px; line-height: 32px;\"><span style=\"font-size: 16px; line-height: 32px;\">";

    private static String part5 = "</span></span>" +
            "</span></a>" +
            "<!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->" +
            "</div>" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 20px; padding-left: 20px; padding-top: 20px; padding-bottom: 30px; font-family: Arial, sans-serif\"><![endif]-->" +
            "<div style=\"color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:20px;padding-right:20px;padding-bottom:30px;padding-left:20px;\">" +
            "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 14px; color: #555555;\">" +
            "<p style=\"font-size: 14px; line-height: 16px; text-align: center; margin: 0;\"><span style=\"font-size: 14px; line-height: 16px;\"><a href=\"" + ApplicationSettings.DOMAIN + "/\" rel=\"noopener\" style=\"text-decoration: underline; color: #296785;\" target=\"_blank\" title=\"website\">Or go to our website</a></span></p>" +
            "</div>" +
            "</div>" +
            "<!--[if mso]></td></tr></table><![endif]-->" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "</div>" +
            "<!--<![endif]-->" +
            "</div>" +
            "</div>" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div style=\"background-color:transparent;\">" +
            "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;;\">" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->" +
            "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top;;\">" +
            "<div style=\"width:100% !important;\">" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">" +
            "<!--<![endif]-->" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">" +
            "<tbody>" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">" +
            "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px; border-collapse: collapse;\" valign=\"top\">" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"0\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 0px solid transparent; height: 0px;\" valign=\"top\" width=\"100%\">" +
            "<tbody>" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">" +
            "<td height=\"0\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; border-collapse: collapse;\" valign=\"top\"><span></span></td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
            "</td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
            "<!--[if (!mso)&(!IE)]><!-->" +
            "</div>" +
            "<!--<![endif]-->" +
            "</div>" +
            "</div>" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->" +
            "</td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
            "<!--[if (IE)]></div><![endif]--></body>" +
            "</html>";
}
