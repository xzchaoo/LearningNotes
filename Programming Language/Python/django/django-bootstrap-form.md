pip install django-bootstrap-form

INSTALLED_APPS = (
    ...
    'bootstrapform',
    ...
)

```
{% load bootstrap %}

{{ form|bootstrap }}

# or use with individual field
{{ form.<field name>|bootstrap }} - To output individual fields

# For horizontal forms
{{ form|bootstrap_horizontal }}

# Or with custom size (default is 'col-lg-2 col-sm-2')
{{ form|bootstrap_horizontal:'col-lg-4' }}
```
