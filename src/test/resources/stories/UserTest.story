Feature: User Feature

Meta:
@user tester
@feature i123
@test JavaClub

Narrative:
    As a tester
    I want to create new user
    So that I can achieve a business goal

Scenario: Create a new user
Meta:
@issue i234

Given New user with lastname Banadiga
And New user with firstname Ihor
And New user with login ibanadiga
When Create user
Then Check login ibanadiga
And Check firstname Ihor
And Check lastname Banadiga


Scenario: Create a other new user

Given Create user with login iverhun firstname Ivan and lastname Verhun
When Create user
Then Check login iverhun
And Check firstname Ivan
And Check lastname Verhun


Scenario: Get user info
Meta:
@issue i987

When Get info for user with login <login>
Then Check login <login>
And Check firstname <firstname>
And Check lastname <lastname>
And Log data

Examples:
| login     | firstname | lastname  |
| iverhun   | Ivan      | Verhun    |
| ibanadiga | Ihor      | Banadiga  |

Scenario: Get user info
Meta:
@issue i456

When Get info for user with login ibanadiga
Then Check login ibanadiga
And Not implemented tasks
And Check firstname Ihor
And Check lastname Banadiga