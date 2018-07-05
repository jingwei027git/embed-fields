# embed-fields

Webapi - Some response model that fields are optional, user  can specify ?embed=description to make optional field enable, if not then optional field will be clear.

For exmaple :

If model is "Book" and ?embed=description,vendor(address),authors(address),contentPreview

this means

1. book.description need output (do not clear)
1. book.vendor.address need output
1. book.authors(List<Authour>) and all althors'address need output
1. book.contentPreview need output
