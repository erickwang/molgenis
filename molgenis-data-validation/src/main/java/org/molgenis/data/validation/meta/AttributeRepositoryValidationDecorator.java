package org.molgenis.data.validation.meta;

import org.molgenis.data.AbstractRepositoryDecorator;
import org.molgenis.data.Repository;
import org.molgenis.data.meta.model.Attribute;
import org.molgenis.data.validation.ValidationException;
import org.molgenis.data.validation.constraint.ValidationResult;
import org.molgenis.data.validation.meta.AttributeValidator.ValidationMode;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class AttributeRepositoryValidationDecorator extends AbstractRepositoryDecorator<Attribute>
{
	private final AttributeValidator attributeValidator;

	public AttributeRepositoryValidationDecorator(Repository<Attribute> delegateRepository,
			AttributeValidator attributeValidator)
	{
		super(delegateRepository);
		this.attributeValidator = requireNonNull(attributeValidator);
	}

	@Override
	public void update(Attribute attr)
	{
		validate(attr, ValidationMode.UPDATE);
		delegate().update(attr);
	}

	@Override
	public void update(Stream<Attribute> attrs)
	{
		delegate().update(attrs.filter(attr ->
		{
			validate(attr, ValidationMode.UPDATE);
			return true;
		}));
	}

	@Override
	public void add(Attribute attr)
	{
		validate(attr, ValidationMode.ADD);
		delegate().add(attr);
	}

	@Override
	public Integer add(Stream<Attribute> attrs)
	{
		return delegate().add(attrs.filter(attr ->
		{
			validate(attr, ValidationMode.ADD);
			return true;
		}));
	}

	private void validate(Attribute attribute, ValidationMode validationMode)
	{
		Collection<? extends ValidationResult> violations = attributeValidator.validate(attribute, validationMode);
		if (!violations.isEmpty())
		{
			throw new ValidationException(violations);
		}
	}
}