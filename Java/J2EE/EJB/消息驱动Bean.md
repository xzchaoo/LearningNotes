@MessageDriven(activationConfig={
	@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Qeueue")
})
impements javax.jms.MessageListener
...

