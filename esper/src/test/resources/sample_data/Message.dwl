%dw 1.0
%output application/java
---
{
	JMSCorrelationID: "????",
	JMSCorrelationIDAsBytes: ["????"],
	JMSDeliveryMode: 1,
	JMSDestination: {
	} as :object {
		class : "javax.jms.Destination"
	},
	JMSExpiration: 1,
	JMSMessageID: "????",
	JMSPriority: 1,
	JMSRedelivered: true,
	JMSReplyTo: {
	} as :object {
		class : "javax.jms.Destination"
	},
	JMSTimestamp: 1,
	JMSType: "????",
	propertyNames: "????"
} as :object {
	class : "javax.jms.Message"
}