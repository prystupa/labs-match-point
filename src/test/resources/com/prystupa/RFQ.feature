Feature: Match-point RFQ logic

  Scenario: RFQ Request by market taker when only one market marker contributes to price (case 1)
    Given market maker "A" quoted the following markets:
      | Day | Bid AMT | Bid | Offer AMT | AMT |
      | 1D  | 10  | 1   | 2     | 15  |
      | 2D  | 50  | 3   | 4     | 20  |
      | 3D  | 35  | 5   | 6     | 10  |
      | 4D  | 15  | 7.5 | 8     | 30  |
      | 5D  | 10  | 9.5 | 10    | 15  |
    Then RFQ service quotes the following price for "4Dx5D":
      | AMT | Bid AMT | Offer AMT | AMT |
      | 10  | 1.5 | 2.5   | 15  |

  Scenario: RFQ Request by market taker when only one market marker contributes to price (case 1)
    Given market maker "B" quoted the following markets:
      | Day | Bid AMT | Bid | Offer AMT | AMT |
      | 1D  | 10  | 1   | 2     | 15  |
      | 2D  | 50  | 3   | 4     | 20  |
      | 3D  | 35  | 5   | 6     | 10  |
      | 4D  | 15  | 7.5 | 8     | 30  |
      | 5D  | 10  | 9.5 | 10    | 15  |
    Then RFQ service quotes the following price for "4Dx5D":
      | AMT | Bid | Offer | AMT |
      | 10  | 1   | 2     | 15  |

  Scenario: RFQ Request by market taker when best price is contributed by two market makers
    Given market maker "A" quoted the following markets:
      | Day | Bid AMT | Bid | Offer AMT | AMT |
      | 1D  | 10  | 1   | 2     | 15  |
      | 2D  | 50  | 3   | 4     | 20  |
      | 3D  | 35  | 5   | 6     | 10  |
      | 4D  | 15  | 7.5 | 8     | 30  |
      | 5D  | 10  | 9.5 | 10    | 15  |
    And market maker "B" quoted the following markets:
      | Day | Bid AMT | Bid | Offer AMT | AMT |
      | 1D  | 10  | 1   | 2     | 15  |
      | 2D  | 50  | 3   | 4     | 20  |
      | 3D  | 35  | 5   | 6     | 10  |
      | 4D  | 15  | 7.5 | 8     | 30  |
      | 5D  | 10  | 9.5 | 10    | 15  |
    Then RFQ service quotes the following price for "4Dx5D":
      | AMT | Bid | Offer | AMT |
      | 10  | 1.5 | 2     | 15  |