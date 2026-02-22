# ToDos to pick up:

## Basic app functionality - top priority (complete these, then you can get a new macbook): 

- [ ] End of race weekend process
  - [ ] Ability for admin to submit results for prediction answers
  - [ ] Work through submitted results to calculate scores for each user
  - [ ] Update user scores and league standings

- [ ] User Predictions management
  - [ ] Allow users to view predictions from previous race weekends, stats on their accuracy etc
  - [ ] Add notifications and reminders for race weekend going live, prediction deadlines, and results posting
  - [ ] Add ability for users to follow other users and see their predictions and performance
  - [ ] Add ability for users to create and join private leagues with friends

## Technical improvements and features:

- [ ] Add authorization levels
  - [ ] Admin vs regular user permissions
  - [ ] Restrict certain actions to admins only
  - [ ] Audit logging for admin actions
  - [ ] Notification system for important events
  
- [ ] Profile management for users
  - [ ] Password reset functionality
  - [ ] Email verification for new users
  - [ ] text verification for new users (maybe)
  - [ ] Ability to change password
  - [ ] Ability to delete account
  - [ ] Ability to update email and other profile details


## dev-ex, reliability and monitoring:

- [ ] PROD deployment
  - Decide on approach for PROD
  - EC2 postgres instance or use RDS?
    

- [ ] Testing
  - Add some tests once core functionality is stable
  - Set up CI/CD pipeline for automated testing and deployment

- [ ] infrastructure improvements
  - Speed up deployment via ansible or terraform etc, ideally one click to pull and run latest stable version
  - Set up monitoring and alerting for application health

- [ ] KMS
  - Add a Key Management Service for handling sensitive data like API keys, passwords etc for PROD deployment
  

- [ ] Metrics and analytics
  - Wire up prometheus and grafana for monitoring of actions and predictions